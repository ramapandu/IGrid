package com.pg.webapp;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;

public class FormulaBar extends BrowseFormula {
	
	private void FormulaBar(){
	
	}
	
	private void initMenu() {

		 Command mycommand = new Command() {
	            private static final long serialVersionUID = 4483012525105015694L;
//	            @Override
//	            public void menuSelected(CustomMenuBar.CustomMenuItem selectedItem) {
//	                
//	                Notification.show("select--"+selectedItem.getId()+"--"+selectedItem.getMenuItem().getCaption()+"--"+selectedItem.getMenuItem().getId());
//	            }
				@Override
				public void menuSelected(MenuItem selectedItem) {
					Notification.show("select--"+selectedItem.getId()+"--");
				}
	        };
		
//browseFormula.addItem("RAN",mycommand);
//browseFormula.addItem("Core", mycommand);
//browseFormula.addItem("VAN", mycommand);
//browseFormula.addItem("IN", mycommand);
//browseFormula.addItem("Transmission", mycommand);
	        
	        MenuItem browse = browseFormula.addItem("Browse", null, null);

	     // Submenu item with a sub-submenu
	     MenuItem ran = browse.addItem("RAN", null, null);
	     ran.addItem("ZTE",
	         mycommand);
	     ran.addItem("Huawei",
	         mycommand);
	     ran.addItem("Ericsson",
		        mycommand);
	     
	     MenuItem core = browse.addItem("Core", null, null);
	     core.addItem("Huawei",
	          mycommand);
	     
	     MenuItem vas = browse.addItem("VAS", null, null);
	     vas.addItem("Huawei",
	          mycommand);
	     
	     MenuItem in = browse.addItem("IN", null, null);
	     in.addItem("Ericsson",
	           mycommand);
	     
	     MenuItem transmission = browse.addItem("Transmission", null, null);
	     transmission.addItem("SPO",
	           mycommand);
	     transmission.addItem("WDM",
	        mycommand);
	     transmission.addItem("MSTP",
		         mycommand);
	     
	}

	MenuBar getFormulaBar(){
		initMenu();	
		return browseFormula;
	}
	
}