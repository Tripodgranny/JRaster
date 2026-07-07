package com.tripod.jraster.asset;

import java.io.InputStream;

public interface ResourceLoader {
  
  InputStream loadResource(String path);

}
