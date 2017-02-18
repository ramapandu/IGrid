package com.pg.webapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Window;

public class UploadXLFile {
    Label label = new Label("Hi!!!...");
    Label status;
    Upload upload;
    Receiver receiver;
    Window mainWindow;
    String originalFileName;
    File file;
	@SuppressWarnings("deprecation")
	public void uploadFile(){
	 mainWindow = new Window("Singleuploadclick Application");
	 HorizontalLayout hl=new HorizontalLayout();   
mainWindow.center();
mainWindow.setModal(true);
mainWindow.setHeight(200,Unit.PIXELS);
mainWindow.setWidth(500, Unit.PIXELS);
	    hl.addComponent(label);

	    status = new Label("Please select a file to upload");
	   
	    
		upload = new Upload(null, getReceiver());

	    upload.setImmediate(true);
	    upload.setButtonCaption("Select file");

	    upload.addListener(new Upload.StartedListener() {
	        private static final long serialVersionUID = 1L;

	        @Override
	        public void uploadStarted(StartedEvent event) {
	            upload.setVisible(false);
	            status.setValue("Uploading file \"" + event.getFilename() + "\"");
	        }
	    });

	    upload.addListener(new Upload.ProgressListener() {
	        private static final long serialVersionUID = 1L;

	        @Override
	        public void updateProgress(long readBytes, long contentLength) {
	        }

	    });

	    upload.addListener(new Upload.SucceededListener() {
	        private static final long serialVersionUID = 1L;

	        @Override
	        public void uploadSucceeded(SucceededEvent event) {
	            status.setValue("Uploading file \"" + event.getFilename() + "\" succeeded");
	        }
	    });

	    upload.addListener(new Upload.FailedListener() {
	        private static final long serialVersionUID = 1L;

	        @Override
	        public void uploadFailed(FailedEvent event) {
	            status.setValue("Uploading interrupted");
	        }
	    });

	    upload.addListener(new Upload.FinishedListener() {
	        private static final long serialVersionUID = 1L;

	        @Override
	        public void uploadFinished(FinishedEvent event) {
	            upload.setVisible(false);
//	            upload.setCaption("Select another file");
	        }
	    });

	    hl.addComponent(status);
	    hl.addComponent(upload); 
	    mainWindow.setContent(hl);
//	    setMainWindow(mainWindow); 
	    getAppUI().getUI().addWindow(mainWindow);
	}
	
	public Upload getUploadStream(){
		uploadFile();
	
		return upload;
	}
	
	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}
	
	
	public Receiver getReceiver(){
	receiver=new Upload.Receiver() {
		private static final long serialVersionUID = -3529828931787975634L;

		@Override
	      public OutputStream receiveUpload(String filename, String mimeType) {
			originalFileName=filename;
	        try {
	        	
	         file = new File(originalFileName, ".xlsx");
//	        	 file = new File(originalFileName, ".xlsx");
	         System.out.println("file path "+file.getName()+" length "+file.length());
	          return new FileOutputStream(file);
	        } catch (IOException e) {
	          e.printStackTrace();
	          return null;
	        }
	      }
	};
	return receiver;
	}
public File getFile(){
	return file;
}
public String getFileName(){
	return originalFileName;
}
}