package com.pg.webapp;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Window;


class UploadFileWindow_2 extends Window implements
            Upload.StartedListener, Upload.ProgressListener,
            Upload.FailedListener, Upload.SucceededListener,
            Upload.FinishedListener {
    
	private Window uploadInfoWindow;
	private static final long serialVersionUID = -3361682642209231417L;
		private  Label state = new Label();
        private  Label result = new Label();
        private  Label fileName = new Label();
        private  Label textualProgress = new Label();
 
        private final ProgressBar progressBar = new ProgressBar();
        private  Button cancelButton;
      
 
        public UploadFileWindow_2(final Upload upload) {
            super("Status");
           
 
            setWidth(350, Unit.PIXELS);
 
            addStyleName("upload-info");
 
            setResizable(false);
            setDraggable(false);
 
            final FormLayout l = new FormLayout();
            setContent(l);
            l.setMargin(true);
            uploadInfoWindow=new Window();
            uploadInfoWindow.setContent(l);
            final HorizontalLayout stateLayout = new HorizontalLayout();
            stateLayout.setSpacing(true);
            stateLayout.addComponent(state);
 
            cancelButton = new Button("Cancel");
            cancelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    upload.interruptUpload();
                }
            });
            cancelButton.setVisible(false);
            cancelButton.setStyleName("small");
            stateLayout.addComponent(cancelButton);
 
            stateLayout.setCaption("Current state");
            state.setValue("Idle");
            l.addComponent(stateLayout);
 
            fileName.setCaption("File name");
            l.addComponent(fileName);
 
            result.setCaption("Line breaks counted");
            l.addComponent(result);
 
            progressBar.setCaption("Progress");
            progressBar.setVisible(false);
            l.addComponent(progressBar);
 
            textualProgress.setVisible(false);
            l.addComponent(textualProgress);
 
            upload.addStartedListener(this);
            upload.addProgressListener(this);
            upload.addFailedListener(this);
            upload.addSucceededListener(this);
            upload.addFinishedListener(this);
 
        }
 
        @Override
        public void uploadFinished(final FinishedEvent event) {
            state.setValue("Idle");
            progressBar.setVisible(false);
            textualProgress.setVisible(false);
            cancelButton.setVisible(false);
        }
 
        @Override
        public void uploadStarted(final StartedEvent event) {
            // this method gets called immediately after upload is started
            progressBar.setValue(0f);
            progressBar.setVisible(true);
            UI.getCurrent().setPollInterval(500);
            textualProgress.setVisible(true);
            // updates to client
            state.setValue("Uploading");
            fileName.setValue(event.getFilename());
 
            cancelButton.setVisible(true);
        }
 
        @Override
        public void updateProgress(final long readBytes,
                final long contentLength) {
            // this method gets called several times during the update
            progressBar.setValue(new Float(readBytes / (float) contentLength));
            textualProgress.setValue("Processed " + readBytes + " bytes of "
                    + contentLength);
//            result.setValue(counter.getLineBreakCount() + " (counting...)");
        }

		@Override
		public void uploadSucceeded(SucceededEvent event) {
			
		}

		@Override
		public void uploadFailed(FailedEvent event) {
			
		}
 
		public Window getUploadWindow(){
			return uploadInfoWindow;
		}
       
    }
 
   