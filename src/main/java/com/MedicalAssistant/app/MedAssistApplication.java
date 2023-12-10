package com.MedicalAssistant.app;

import com.MedicalAssistant.app.Handlers.Doctor.DoctorAuthorizationHandler;
import com.MedicalAssistant.app.Handlers.Doctor.DoctorDashboardHandler;
import com.MedicalAssistant.app.Handlers.HomeHandler;
import com.MedicalAssistant.app.Handlers.LoginFormHandler;
import com.MedicalAssistant.app.Handlers.LoginSubmissionHandler;
import com.MedicalAssistant.app.Handlers.Patients.*;
import com.MedicalAssistant.app.Handlers.Tehnician.TechnicianAuthorizationHandler;
import com.MedicalAssistant.app.Handlers.Tehnician.TechnicianDashboardHandler;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;


public class MedAssistApplication {

	public static void main(String[] args) {
		Javalin app = Javalin.create(config -> {
			config.staticFiles.add(System.getenv("MA_RESOURCES") + "/static", Location.EXTERNAL);
		});

		app.get("/", new HomeHandler());
		app.get("/login", new LoginFormHandler());
		app.post("/login", new LoginSubmissionHandler());

		app.before("/doctor", new DoctorAuthorizationHandler());
		app.before("/doctor/*", new DoctorAuthorizationHandler());
		app.get("/doctor", new DoctorDashboardHandler());

		app.before("/technician", new TechnicianAuthorizationHandler());
		app.before("/technician/*", new TechnicianAuthorizationHandler());
		app.get("/technician", new TechnicianDashboardHandler());

		app.post("/technician/new_patient", new NewPatientSubmitHandler());
		app.get("/technician/new_patient", new NewPatientHandler());

		app.get("/technician/all_patients", new AllPatientHandler());

		app.get("/technician/find_patient", new FindPatientsHandler());

		app.post("/technician/edit/{patient_id}", new EditPatientSubmitHandler());
		app.get("/technician/edit/{patient_id}", new EditPatientHandler());

		app.start(9000);
	}
}
