package com.pg.webapp;
import com.pg.webapp.BrowseFormula;
import com.pg.webapp.Settings2;
import com.vaadin.ui.MenuBar;

public class FormulaBar extends BrowseFormula {
	
	private void FormulaBar(){
	initMenu();	
	}
	
	private void initMenu() {
browseFormula.addItem("RAN", null);	
browseFormula.addItem("Core", null);
browseFormula.addItem("VAN", null);
browseFormula.addItem("IN", null);
browseFormula.addItem("Transmission", null);
	}

	MenuBar getFormulaBar(){
		return browseFormula;
	}
	
}