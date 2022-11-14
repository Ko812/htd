<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	JobSeeker js = (JobSeeker) session.getAttribute("logged-in-job-seeker");
	String firstName = js.getFirstName();
	String lastName = js.getLastName();
	String userName = js.getUserName();
	String email = js.getEmail();
	String NRIC = js.getNRIC();
	String contact = js.getContact();
	int yearsOfExp = js.getYearsOfExperience();
	List<Acad> acads = new ArrayList<Acad>();
	if(js.getAcadDetails() != null){
		acads = js.getAcadDetails();
	}
	List<String> workExps = new ArrayList<String>();
	if(js.getWorkExp() != null){
		workExps = js.getWorkExp();
	}
	List<String> awards = new ArrayList<String>();
	if(js.getAwards() != null){
		awards = js.getAwards();
	}
	
%>
<div class="emp-main-content form-vertical-overflow-auto">
	
	<table>
		<tr>
			<th>Name</th>
			<th>User Name</th>
			<th>Email</th>
			<th>NRIC</th>
			<th>Contact No.</th>
		</tr>
		<tr>
			<td><%=firstName + " " + lastName%></td>
			<td><%=userName%></td>
			<td><%=email%></td>
			<td><%=NRIC%></td>
			<td><%=contact%></td>
		</tr>
	</table>
	<br><br>
	<form class="form-max-width border rounded-3 bg-light" id="credentials-form"
		action="/job-portal/AddCredentials">
		<div class="form-title">Add Personal Credentials</div>
		
		<div class="form-group emp-form-row-style">
			<label for="floatingYearsOfExp" class="form-label">Years of Experience</label>
			<input type="number" name="yearsOfExp" class="form-control"
				placeholder="Years of Experience" id="floatingYearsOfExp" min=0
				value="<%=yearsOfExp%>">
		</div>
		
		<div id="acadFormInputs" class="form-subsection">
		<div class="form-sub-title-box">
			<div class="form-sub-title">Academic</div>
			<button type="button" onclick="addNewAcad('<%=acads.size()+2%>')" class="add-cred-button">Add New</button>
			&nbsp;&nbsp;<div id="maxAcadEntryMessage" style="display:none; color:red">Max. 5 academic credentials</div>
		</div>
		
		<%
			for(int i=1;i<=acads.size();i++){
				Acad acad = acads.get(i-1);
				%>
		<div class="form-group emp-form-row-style">
			<label for="floatingAcademic<%=i%>" class="form-label">Academic Credential <%=i%></label> 
			<select name="acad<%=i%>" class="form-control" id="floatingAcademic<%=i%>">
				
				<option value="AdvancedCertification" <%=acad==Acad.AdvancedCertification?"selected":""%>>Advanced Certification</option>
				<option value="Bachelor" <%=acad==Acad.Bachelor?"selected":""%>>Bachelor</option>
				<option value="Diploma" <%=acad==Acad.Diploma?"selected":""%>>Diploma</option>
				<option value="PhD" <%=acad==Acad.PhD?"selected":""%>>PhD</option>
				<option value="Master" <%=acad==Acad.Master?"selected":""%>>Master</option>
				<option value="ProfessionalCertification" <%=acad==Acad.ProfessionalCertification?"selected":""%>>Professional Certification</option>
			</select>
		</div>
		<%
			}
			if(acads.size() < 5){
				int nextI = acads.size() + 1;
		%>
		<div class="form-group emp-form-row-style">
			<label for="floatingAcademic<%=nextI%>" class="form-label">Academic Credential <%=nextI%></label> 
			<select name="acad<%=nextI%>" class="form-control" id="floatingAcademic<%=nextI%>">
				<option value="" selected></option>
				<option value="AdvancedCertification">Advanced Certification</option>
				<option value="Bachelor">Bachelor</option>
				<option value="Diploma">Diploma</option>
				<option value="PhD">PhD</option>
				<option value="Master">Master</option>
				<option value="ProfessionalCertification">Professional Certification</option>
			</select>
		</div>
		</div>
		<%
		}%>
		<div id="workExpFormInputs" class="form-subsection">
		
		<div class="form-sub-title-box">
			<div class="form-sub-title">Work Experience</div>
			<button type="button" onclick="addNewWorkExp('<%=workExps.size()+2%>')" class="add-cred-button">Add New</button>
			&nbsp;&nbsp;<div id="maxWorkEntryMessage" style="display:none; color:red">Max. 5 work experience credentials</div>
		</div>
		
		<%
			for(int i=1;i<=workExps.size();i++){
		%>
		
		<div class="form-group emp-form-row-style">
			<label for="floatingWorkExp<%=i%>" class="form-label">Work Experience <%=i%></label>
			<textarea name="workExp<%=i%>" class="form-control" placeholder="Work Experience" id="floatingWorkExp<%=i%>"><%=workExps.get(i-1)%></textarea>
		</div>
		<%}
			if(workExps.size() < 5){
				int nextJ = workExps.size() + 1;
		%>
		<div class="form-group emp-form-row-style">
			<label for="floatingWorkExp<%=nextJ%>" class="form-label">Work Experience <%=nextJ%></label>
			<textarea  name="workExp<%=nextJ%>" class="form-control" placeholder="Work Experience" id="floatingWorkExp<%=nextJ%>"></textarea>
		</div>
		<%}%>
		</div>
		
		<div id="awardFormInputs" class="form-subsection">
		<div class="form-sub-title-box">
			<div class="form-sub-title">Awards</div>
			<button type="button" onclick="addNewAward('<%=awards.size()+2%>')" class="add-cred-button">Add New</button>
			<div id="maxAwardEntryMessage" style="display:none; color:red">Max. 5 award credentials</div>
		</div>
		
		<%
			for(int i=1;i<=awards.size();i++){
		%>
		
		<div class="form-group emp-form-row-style">
			<label for="floatingAward<%=i%>" class="form-label">Award <%=i%></label>
			<input type="text" name="award<%=i%>" class="form-control"
				placeholder="Award" id="floatingAward<%=i%>"
				value="<%=awards.get(i-1)%>">
		</div>
		<%}
			if(awards.size() < 5){
				int nextK = awards.size() + 1;
		%>
		<div class="form-group emp-form-row-style">
			<label for="floatingAward<%=nextK%>" class="form-label">Award <%=nextK%></label>
			<input type="text" name="award<%=nextK%>" class="form-control"
				placeholder="Award" id="floatingAward<%=nextK%>"
				value="">
		</div>
		</div>
		<%} %>
		<%
			String formMessage = (String) session.getAttribute("update-js-form-message");
			Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
			if(formMessage != null && !formMessageRead){
				session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
			%>
				<div class="form-message">
					<%=formMessage%>
				</div>
		<%}%>
		<div class="row emp-form-row-style">
			<div class="row-item">
				<button type="button" onclick="back()" class="login-button">Back</button>
			</div>
			<div class="row-item">
				<input type="submit" value="Save" class="login-button">
			</div>
		</div>
	</form>
	<br><br><br><br><br><br>
	<script>
	const back = () => {
		history.back();
	}
	
	const acadFormInputsEl = document.getElementById("acadFormInputs");
    const workExpFormInputsEl = document.getElementById("workExpFormInputs");
    const awardFormInputsEl = document.getElementById("awardFormInputs");
    const genericEl = document.getElementById("floatingAcademic1");
    const genericWidth = genericEl.style.width;
    var currentAcadCount = 0;
    var currentWorkCount = 0;
    var currentAwardCount = 0;
    const addNewAcad = function(num){
    	if(currentAcadCount==0){
    		currentAcadCount = Number(num);
    	}else{
    		num = Math.max(Number(num), currentAcadCount);
    	}
    	console.log("adding acad " + num + " " + currentAcadCount);
    	if(Number(num) < 6){
    		const newDivEl = document.createElement("div");
            newDivEl.classList.add(["form-group", "emp-form-row-style"]);
            newDivEl.style.minWidth = "300px";
        	newDivEl.style.maxWidth = "700px";
        	newDivEl.style.margin = "8px 0px";
            const newLabelEl = document.createElement("label");
            newLabelEl.for = "acad"+num;
            newLabelEl.classList.add("form-label");
            newLabelEl.innerHTML = "Academic Credential " + num;
            const newSelectEl = document.createElement("select");
            newSelectEl.name = "acad"+num;
            newSelectEl.classList.add(["form-control"]);
            newSelectEl.id="floatingAcademic"+num;
            const blankOptionEl = document.createElement("option");
            blankOptionEl.value="";
            blankOptionEl.selected=true;
            blankOptionEl.innerHTML="";
            const advancedCertOptionEl = document.createElement("option"); 
            advancedCertOptionEl.value = "AdvancedCertification";
            advancedCertOptionEl.innerHTML = "Advanced Certification";
            const bachelorOptionEl = document.createElement("option");
            bachelorOptionEl.value = "Bachelor";
            bachelorOptionEl.innerHTML = "Bachelor";
            const diplomaOptionEl = document.createElement("option");
            diplomaOptionEl.value = "Diploma";
            diplomaOptionEl.innerHTML = "Diploma";
            const phDOptionEl = document.createElement("option");
            phDOptionEl.value = "PhD";
            phDOptionEl.innerHTML = "PhD";
            const masterOptionEl = document.createElement("option");
            masterOptionEl.value = "Master";
            masterOptionEl.innerHTML = "Master";
            const professionalCertOptionEl = document.createElement("option");
            professionalCertOptionEl.value = "ProfessionalCertification";
            professionalCertOptionEl.innerHTML = "Professional Certification";
            newSelectEl.appendChild(blankOptionEl);
            newSelectEl.appendChild(advancedCertOptionEl);
            newSelectEl.appendChild(bachelorOptionEl);
            newSelectEl.appendChild(diplomaOptionEl);
            newSelectEl.appendChild(phDOptionEl);
            newSelectEl.appendChild(masterOptionEl);
            newSelectEl.appendChild(professionalCertOptionEl);
            newDivEl.appendChild(newLabelEl);
            newDivEl.appendChild(newSelectEl);
            acadFormInputsEl.appendChild(newDivEl);
            currentAcadCount++;
    	}else {
    		const maxAcadEntryMessageEl = document.getElementById("maxAcadEntryMessage");
    		maxAcadEntryMessageEl.style.display = "block";
    	}
        
    }
    
    const addNewWorkExp = (num) => {
    	if(currentWorkCount==0){
    		currentWorkCount = Number(num);
    	}else{
    		num = Math.max(Number(num), currentWorkCount);
    	}
    	if(Number(num) < 6){
    		const newDivEl = document.createElement("div");
            newDivEl.classList.add(["form-group", "emp-form-row-style"]);
            newDivEl.style.minWidth = "300px";
        	newDivEl.style.maxWidth = "700px";
        	newDivEl.style.margin = "8px 0px";
            const newLabelEl = document.createElement("label");
            newLabelEl.for = "floatingWorkExp"+num;
            newLabelEl.classList.add("form-label");
            newLabelEl.innerHTML = "Work Experience " + num;
            const newTextAreaEl = document.createElement("textarea");
            newTextAreaEl.name = "workExp"+num;
            newTextAreaEl.classList.add("form-control");
            newTextAreaEl.placeholder="Work Experience";
            newTextAreaEl.id="floatingWorkExp"+num;
            newTextAreaEl.maxLength = "255";
            newDivEl.appendChild(newLabelEl);
            newDivEl.appendChild(newTextAreaEl);
            newDivEl.style.width = genericWidth;
            workExpFormInputsEl.appendChild(newDivEl);
            currentWorkCount++;
    	}else {
    		const maxWorkEntryMessageEl = document.getElementById("maxWorkEntryMessage");
    		maxWorkEntryMessageEl.style.display = "block";
    	}
        
    }

    const addNewAward = (num) => {
    	if(currentAwardCount==0){
    		currentAwardCount = Number(num);
    	}else{
    		num = Math.max(Number(num), currentAwardCount);
    	}
    	if(Number(num) < 6){
    		const newDivEl = document.createElement("div");
            newDivEl.classList.add(["form-group", "emp-form-row-style"]);
            newDivEl.style.minWidth = "300px";
        	newDivEl.style.maxWidth = "700px";
        	newDivEl.style.margin = "8px 0px";
            const newLabelEl = document.createElement("label");
            newLabelEl.for = "floatingAward"+num;
            newLabelEl.classList.add("form-label");
            newLabelEl.innerHTML = "Award " + num;
            const newInputEl = document.createElement("input");
            newInputEl.type = "text";
            newInputEl.maxLength = "50";
            newInputEl.name = "award" + num;
            newInputEl.classList.add("form-control");
            newInputEl.placeholder = "Award";
            newInputEl.id = "floatingAward" + num;
            newDivEl.appendChild(newLabelEl);
            newDivEl.appendChild(newInputEl);
            newDivEl.style.width = genericWidth;
            awardFormInputsEl.appendChild(newDivEl);
            currentAwardCount++;
    	}else {
    		const maxAwardEntryMessageEl = document.getElementById("maxAwardEntryMessage");
    		maxAwardEntryMessageEl.style.display = "block";
    	}
        
    }
	</script>
</div>
