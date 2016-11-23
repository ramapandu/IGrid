package com.pg.webapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pg.webapp.database.DbConnection;
import com.pg.webapp.domain.User;
import com.pg.webapp.security.LDAPSearch;
import com.pg.webapp.security.ValidateLogin;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends CustomComponent implements View {

	private static final long serialVersionUID = 1571188358428569977L;

	public static final String NAME = "login";

	String username;
	String password;
	SpreadsheetDemoUI ui;
	VerticalLayout mainLayout;
	TextField emailField;
	PasswordField passwordField;

	// final String path = "/resources";

	public LoginView(final Navigator navigator) {
		setSizeFull();
		mainLayout = new VerticalLayout();
		mainLayout.setStyleName("backgroundimage");
		setCompositionRoot(mainLayout);

		FormLayout loginForm = new FormLayout();
		this.ui = getAppUI();

		emailField = new TextField("Email");
		loginForm.addComponent(emailField);
		emailField.setStyleName("labelred");

		passwordField = new PasswordField("Password");
		passwordField.setStyleName("labelred");
		loginForm.addComponent(passwordField);

		final Button loginButton = new Button("Login",
				new Button.ClickListener() {
					private static final long serialVersionUID = -892611583835722967L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							validateLogin();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				});

		final Button cancelButton = new Button("Cancel",
				new Button.ClickListener() {

					private static final long serialVersionUID = -2766935020155002360L;

					@Override
					public void buttonClick(ClickEvent event) {

						
					}

					
				});

		HorizontalLayout buttonsContainer = new HorizontalLayout();
		buttonsContainer.addComponent(loginButton);
		buttonsContainer.addComponent(cancelButton);
		Label note = new Label(
				"Login with Username:test@test.com and Password:test12345 to use the application");
		note.setStyleName("labelwhite");
		buttonsContainer.addComponent(note);
		buttonsContainer.setSpacing(true);
		loginForm.addComponent(buttonsContainer);
		loginForm.setComponentAlignment(buttonsContainer,
				Alignment.MIDDLE_CENTER);
		loginForm.setSpacing(true);
		mainLayout.addComponent(loginForm);
		mainLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}

	public void validateLogin() throws Exception {
		username = emailField.getValue();
		password = passwordField.getValue();
		String dbUser,dbPassword;

		try {
//			LDAPSearch ldapSearch=new LDAPSearch();
//			ldapSearch.Authenticate();
			
//			ValidateLogin vl=new ValidateLogin();
//			vl.validateLogin("c5245576", "Rp223366!");
			DbConnection conn=new DbConnection();
			Statement stmt=conn.getConnection().createStatement();  
			ResultSet rs=stmt.executeQuery("select * from users");  
			while(rs.next())  {
			System.out.println(rs.getString(2)+"  "+rs.getString(3));  
			dbUser=rs.getString(2);
			dbPassword=rs.getString(3);
			if (username.equals(dbUser)
					&& password.equals(dbPassword)) {
				Notification.show("Welcome " + username);
				getAppUI().getUser().setLoggedInUser(username);
				getAppUI().getNavigator().navigateTo("sheet");
				

			} 
			if(getAppUI().getUser().getLoggedInUser()==null)
				Notification.show("User name OR Password is wrong");
			}
			conn.getConnection().close(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
								
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		emailField.focus();
	}

	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}

}