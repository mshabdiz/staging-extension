package org.exoplatform.management.ecmadmin.operations.script;

import org.apache.commons.io.IOUtils;
import org.exoplatform.management.ecmadmin.operations.ECMAdminImportResource;
import org.exoplatform.services.cms.scripts.CmsScript;
import org.exoplatform.services.cms.scripts.ScriptService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.gatein.management.api.exceptions.OperationException;
import org.gatein.management.api.operation.OperationContext;
import org.gatein.management.api.operation.OperationNames;
import org.gatein.management.api.operation.ResultHandler;
import org.gatein.management.api.operation.model.NoResultModel;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author <a href="mailto:bkhanfir@exoplatform.com">Boubaker Khanfir</a>
 * @version $Revision$
 */
public class ScriptImportResource extends ECMAdminImportResource {
  private static final Log log = ExoLogger.getLogger(ScriptImportResource.class);
  private ScriptService scriptService = null;

  public ScriptImportResource() {
    super(null);
  }

  public ScriptImportResource(String filePath) {
    super(filePath);
  }

  public void execute(OperationContext operationContext, ResultHandler resultHandler) throws OperationException {
    // get attributes and attachement inputstream
    super.execute(operationContext, resultHandler);

    if (scriptService == null) {
      scriptService = operationContext.getRuntimeContext().getRuntimeComponent(ScriptService.class);
    }

    SessionProvider systemSessionProvider = SessionProvider.createSystemProvider();
    try {
      ZipInputStream zin = new ZipInputStream(attachmentInputStream);
      ZipEntry ze = null;
      while ((ze = zin.getNextEntry()) != null) {
        if (!ze.getName().startsWith("ecmadmin/script/")) {
          continue;
        }
        String path = ze.getName().substring("ecmadmin/script/".length());
        CmsScript script = null;
        try {
          script = scriptService.getScript(path);
        } catch (ClassNotFoundException exception) {
          // Script doesn't exist
        }
        if (script != null) {
          if (replaceExisting) {
            log.info("Overwrite existing script '" + path + "'.");
          } else {
            log.info("Ignore existing script'" + path + "'.");
            continue;
          }
        }
        String data = IOUtils.toString(zin, "UTF-8");
        scriptService.addScript(path, data, systemSessionProvider);
        zin.closeEntry();
      }
      zin.close();
      resultHandler.completed(NoResultModel.INSTANCE);
    } catch (Exception exception) {
      throw new OperationException(OperationNames.IMPORT_RESOURCE, "Error while importing ECMS scripts.", exception);
    }
  }
}