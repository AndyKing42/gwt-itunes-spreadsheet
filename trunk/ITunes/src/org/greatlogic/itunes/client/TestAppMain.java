package org.greatlogic.itunes.client;

/**********************************************
 * Copyright (C) 2011 Lukas laag
 * This file is part of lib-gwt-file.
 * 
 * lib-gwt-file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * lib-gwt-file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with lib-gwt-file.  If not, see http://www.gnu.org/licenses/
 **********************************************/

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.ArrayList;
import java.util.List;
import org.vectomatic.dnd.DataTransferExt;
import org.vectomatic.dnd.DropPanel;
import org.vectomatic.file.ErrorCode;
import org.vectomatic.file.File;
import org.vectomatic.file.FileError;
import org.vectomatic.file.FileList;
import org.vectomatic.file.FileReader;
import org.vectomatic.file.FileUploadExt;
import org.vectomatic.file.events.ErrorEvent;
import org.vectomatic.file.events.ErrorHandler;
import org.vectomatic.file.events.LoadEndEvent;
import org.vectomatic.file.events.LoadEndHandler;

public class TestAppMain implements EntryPoint {
@UiField
Button                   resetBtn;
@UiField
Button                   browseBtn;
@UiField
DropPanel                dropPanel;
@UiField
FileUploadExt            fileUpload;
@UiField
FileUploadExt            customUpload;
@UiField
FlowPanel                imagePanel;

@UiField(provided = true)
static TestAppMainBundle bundle = GWT.create(TestAppMainBundle.class);
protected FileReader     reader;
protected List<File>     readQueue;

interface TestAppMainBinder extends UiBinder<FlowPanel, TestAppMain> {
}
private static TestAppMainBinder binder = GWT.create(TestAppMainBinder.class);
interface TestAppMainCss extends CssResource {
public String imagePanel();
public String customUpload();
public String dropPanel();
public String thumbnail();
@ClassName("thumbnail-image")
public String thumbnailImage();
@ClassName("thumbnail-text")
public String thumbnailText();
}
interface TestAppMainBundle extends ClientBundle {
@Source("TestAppMainCss.css")
public TestAppMainCss css();
}

@Override
public void onModuleLoad() {
  bundle.css().ensureInjected();
  FlowPanel flowPanel = binder.createAndBindUi(this);
  Document document = Document.get();
  dropPanel.getElement().appendChild(document.createDivElement()).appendChild(document.createTextNode("Drop files here"));
  RootLayoutPanel.get().add(flowPanel);

  reader = new FileReader();
  reader.addLoadEndHandler(new LoadEndHandler() {
    @Override
    public void onLoadEnd(LoadEndEvent event) {
      if (reader.getError() == null) {
        String stringResult = reader.getStringResult();
        if (readQueue.size() > 0) {
          File file = readQueue.get(0);
          try {
          }
          finally {
            readQueue.remove(0);
            readNext();
          }
        }
      }
    }
  });

  reader.addErrorHandler(new ErrorHandler() {
    @Override
    public void onError(ErrorEvent event) {
      if (readQueue.size() > 0) {
        File file = readQueue.get(0);
        handleError(file);
        readQueue.remove(0);
        readNext();
      }
    }
  });
  readQueue = new ArrayList<File>();
}

private void handleError(File file) {
  FileError error = reader.getError();
  String errorDesc = "";
  if (error != null) {
    ErrorCode errorCode = error.getCode();
    if (errorCode != null) {
      errorDesc = ": " + errorCode.name();
    }
  }
  Window.alert("File loading error for file: " + file.getName() + "\n" + errorDesc);
}

private void setBorderColor(String color) {
  dropPanel.getElement().getStyle().setBorderColor(color);
}

private void processFiles(FileList files) {
  GWT.log("length=" + files.getLength());
  for (File file : files) {
    readQueue.add(file);
  }
  readNext();
}

private void readNext() {
  if (readQueue.size() > 0) {
    File file = readQueue.get(0);
    String type = file.getType();
    try {
      if (type != null && type.startsWith("text")) {
        reader.readAsText(file);
      }
    }
    catch (Throwable t) {
      // Necessary for FF (see bug https://bugzilla.mozilla.org/show_bug.cgiid=701154)
      // Standard-complying browsers will to go in this branch
      handleError(file);
      readQueue.remove(0);
      readNext();
    }
  }
}

private static native String base64encode(String str) /*-{
	return $wnd.btoa(str);
}-*/;

@UiHandler("browseBtn")
public void browse(ClickEvent event) {
  customUpload.click();
}

@UiHandler("resetBtn")
public void reset(ClickEvent event) {
  for (int i = imagePanel.getWidgetCount() - 1; i >= 0; i--) {
    imagePanel.remove(i);
  }
}

@UiHandler("fileUpload")
public void uploadFile1(ChangeEvent event) {
  processFiles(fileUpload.getFiles());
}

@UiHandler("customUpload")
public void uploadFile2(ChangeEvent event) {
  processFiles(customUpload.getFiles());
}

@UiHandler("dropPanel")
public void onDragOver(DragOverEvent event) {
  // Mandatory handler, otherwise the default
  // behavior will kick in and onDrop will never
  // be called
  event.stopPropagation();
  event.preventDefault();
}

@UiHandler("dropPanel")
public void onDragEnter(DragEnterEvent event) {
  event.stopPropagation();
  event.preventDefault();
}

@UiHandler("dropPanel")
public void onDragLeave(DragLeaveEvent event) {
  event.stopPropagation();
  event.preventDefault();
}

@UiHandler("dropPanel")
public void onDrop(DropEvent event) {
  processFiles(event.getDataTransfer().<DataTransferExt> cast().getFiles());
  event.stopPropagation();
  event.preventDefault();
}

}