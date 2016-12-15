package com.pg.webapp;
import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class FormulaBar extends BrowseFormula {
	MenuItem gridMenu;
	MenuItem browseMenu;
	
	private void FormulaBar(){
		
	
	}
	
	public MenuBar getMenuBar(){
		getGridMenuBar();
	    getFormulaBar();
		return browseFormula;
	}

	private void getGridMenuBar() {
		 gridMenu = browseFormula.addItem("FILE", null, null);
		 
		Command newGridCommand = new Command() {
            private static final long serialVersionUID = 4483012525105015694L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
				
				getAppUI().getSpreadsheet_dao().getSpreadsheet().createNewSheet("new", 100, 30);
//				Spreadsheet s=new Spreadsheet();
//				s.createNewSheet("new", 100, 30);
			}
        };
        Command openGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
				
			}
        };
        Command saveGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
			}
        };
        Command closeGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
//				getAppUI().getSpreadsheet_dao().getSpreadsheet().
			}
        };
        Command refreshGridCommand = new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
//				Notification.show("select--"+selectedItem.getId()+"--");
				getAppUI().getSpreadsheet_dao().getSpreadsheet().refreshAllCellValues();
			}
        };
        
        MenuItem new1 = gridMenu.addItem("New Grid", newGridCommand);
//        new1.addSeparator();
        MenuItem open1 = gridMenu.addItem("Open Grid", openGridCommand);
//        open1.addSeparator();
        MenuItem save1 = gridMenu.addItem("Save Grid", saveGridCommand);
//        save1.addSeparator();
        MenuItem close1 = gridMenu.addItem("Close Grid", closeGridCommand);
//        close1.addSeparator();
        MenuItem refresh1 = gridMenu.addItem("Refresh", refreshGridCommand);
//        refresh1.addSeparator();
	}


	MenuBar getFormulaBar(){
		Command mycommand = new Command() {
            private static final long serialVersionUID = 4483012525105015694L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("select--"+selectedItem.getId()+"--");
			}
        };
	
        browseMenu = browseFormula.addItem("Browse", null, null);

     // Submenu item with a sub-submenu
     MenuItem ran = browseMenu.addItem("RAN", null, null);
     ran.addItem("ZTE",
         mycommand);
     ran.addItem("Huawei",
         mycommand);
     ran.addItem("Ericsson",
	        mycommand);
     
     MenuItem core = browseMenu.addItem("Core", null, null);
     core.addItem("Huawei",
          mycommand);
     
     MenuItem vas = browseMenu.addItem("VAS", null, null);
     vas.addItem("Huawei",
          mycommand);
     
     MenuItem in = browseMenu.addItem("IN", null, null);
     in.addItem("Ericsson",
           mycommand);
     
     MenuItem transmission = browseMenu.addItem("Transmission", null, null);
     transmission.addItem("SPO",
           mycommand);
     transmission.addItem("WDM",
        mycommand);
     transmission.addItem("MSTP",
	         mycommand);
		
		return browseFormula;
	}
	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}
	
	
}