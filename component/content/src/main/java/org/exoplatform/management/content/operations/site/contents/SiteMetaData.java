package org.exoplatform.management.content.operations.site.contents;

import java.util.HashMap;
import java.util.Map;

public class SiteMetaData {

  public static final String SITE_PATH = "site-path";
  public static final String SITE_WORKSPACE = "site-workspace";
  public static final String SITE_NAME = "site-name";

  Map<String, String> options = new HashMap<String, String>();
  Map<String, String> exportedFiles = new HashMap<String, String>();
  Map<String, String> exportedHistoryFiles = new HashMap<String, String>();
  Map<String, NodeMetadata> nodesMetadata = new HashMap<String, NodeMetadata>();

  public Map<String, String> getExportedFiles() {
    return this.exportedFiles;
  }

  public Map<String, String> getOptions() {
    return this.options;
  }

  public void setExportedFiles(Map<String, String> jcrExportedFiles) {
    this.exportedFiles = jcrExportedFiles;
  }

  public void setOptions(Map<String, String> options) {
    this.options = options;
  }

  public Map<String, String> getExportedHistoryFiles() {
    return exportedHistoryFiles;
  }

  public void setExportedHistoryFiles(Map<String, String> exportedHistoryFiles) {
    this.exportedHistoryFiles = exportedHistoryFiles;
  }

  public Map<String, NodeMetadata> getNodesMetadata() {
    return nodesMetadata;
  }

  public void setNodesMetadata(Map<String, NodeMetadata> nodesMetadata) {
    this.nodesMetadata = nodesMetadata;
  }

}
