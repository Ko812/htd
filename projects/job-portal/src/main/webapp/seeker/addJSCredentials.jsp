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
	String flow = request.getParameter("flow");
	if(flow != null && flow.equals("null")){
		session.setAttribute("flow", null);
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
		
		<div class="form-group cred-form-row">
			<label for="floatingYearsOfExp" class="cred-form-label">Years of Experience</label>
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
		<div class="form-group cred-form-row" id="acad-<%=i%>">
			<label for="floatingAcademic<%=i%>" class="cred-form-label">Academic Credential <%=i%></label> 
			<select name="acad<%=i%>" class="form-control" id="floatingAcademic<%=i%>">
				<option value="AdvancedCertification" <%=acad==Acad.AdvancedCertification?"selected":""%>>Advanced Certification</option>
				<option value="Bachelor" <%=acad==Acad.Bachelor?"selected":""%>>Bachelor</option>
				<option value="Diploma" <%=acad==Acad.Diploma?"selected":""%>>Diploma</option>
				<option value="PhD" <%=acad==Acad.PhD?"selected":""%>>PhD</option>
				<option value="Master" <%=acad==Acad.Master?"selected":""%>>Master</option>
				<option value="ProfessionalCertification" <%=acad==Acad.ProfessionalCertification?"selected":""%>>Professional Certification</option>
			</select>
			<button class="remove-cred-button" type="button" onclick="removeAcad('<%=i%>')">
				<svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        			<use xlink:href="/job-portal/resources/fa/solid.svg#square-xmark"></use>
      			</svg>
      		</button>
		</div>
		<%
			}
			if(acads.size() < 5){
				int nextI = acads.size() + 1;
		%>
		<div class="form-group cred-form-row" id="acad-<%=nextI%>">
			<label for="floatingAcademic<%=nextI%>" class="cred-form-label">Academic Credential <%=nextI%></label> 
			<select name="acad<%=nextI%>" class="form-control" id="floatingAcademic<%=nextI%>">
				<option value="" selected></option>
				<option value="AdvancedCertification">Advanced Certification</option>
				<option value="Bachelor">Bachelor</option>
				<option value="Diploma">Diploma</option>
				<option value="PhD">PhD</option>
				<option value="Master">Master</option>
				<option value="ProfessionalCertification">Professional Certification</option>
			</select>
			<button class="remove-cred-button" type="button" onclick="removeAcad('<%=nextI%>')">
				<svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        			<use xlink:href="/job-portal/resources/fa/solid.svg#square-xmark"></use>
      			</svg>
      		</button>
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
		
		<div class="form-group work-cred-form-group" id="work-<%=i%>">
			<div class="work-cred-row">
				<label for="floatingWorkExp<%=i%>" class="cred-form-label">Work Experience <%=i%></label><button class="remove-work-button" type="button" onclick="removeWork('<%=i%>')">
					<svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        				<use xlink:href="/job-portal/resources/fa/solid.svg#square-xmark"></use>
      				</svg>
				</button>
			</div>
			<textarea maxlength="255" name="workExp<%=i%>" class="form-control" placeholder="Work Experience" id="floatingWorkExp<%=i%>"><%=workExps.get(i-1)%></textarea>
		</div>
		<%}
			if(workExps.size() < 5){
				int nextJ = workExps.size() + 1;
		%>
		<div class="form-group work-cred-form-group" id="work-<%=nextJ%>">
			<div class="work-cred-row">
				<label for="floatingWorkExp<%=nextJ%>" class="cred-form-label">Work Experience <%=nextJ%></label><button class="remove-work-button" type="button" onclick="removeWork('<%=nextJ%>')">
					<svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        				<use xlink:href="/job-portal/resources/fa/solid.svg#square-xmark"></use>
      				</svg>
				</button>
			</div>
			<textarea maxlength="255" name="workExp<%=nextJ%>" class="form-control" placeholder="Work Experience" id="floatingWorkExp<%=nextJ%>"></textarea>
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
		
		<div class="form-group cred-form-row" id="award-<%=i%>">
			<label for="floatingAward<%=i%>" class="cred-form-label">Award <%=i%></label>
			<input type="text" name="award<%=i%>" class="form-control"
				placeholder="Award" id="floatingAward<%=i%>" maxlength="50"
				value="<%=awards.get(i-1)%>">
			<button class="remove-cred-button" type="button" onclick="removeAward(<%=i%>)">
				<svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        			<use xlink:href="/job-portal/resources/fa/solid.svg#square-xmark"></use>
      			</svg>
			</button>
		</div>
		<%}
			if(awards.size() < 5){
				int nextK = awards.size() + 1;
		%>
		<div class="form-group cred-form-row" id="award-<%=nextK%>">
			<label for="floatingAward<%=nextK%>" class="cred-form-label">Award <%=nextK%></label>
			<input type="text" name="award<%=nextK%>" class="form-control"
				placeholder="Award" id="floatingAward<%=nextK%>" maxlength="50"
				value="">
			<button class="remove-cred-button" type="button" onclick="removeAward(<%=nextK%>)">
				<svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        			<use xlink:href="/job-portal/resources/fa/solid.svg#square-xmark"></use>
      			</svg>
			</button>
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
	
	const renumberLabels = (tag, type) => {
    	const tagContainer = document.getElementById(tag);
    	let tagCount = 1;
    	console.log("children count " + tagContainer.children.length);
    	for(let child of tagContainer.children){
    		if(child.getAttribute("class").includes("form-group")){
    			if(type === "acad" || type === "award"){
    				let tagLabel = child.children[0];
        			tagLabel.innerHTML = tagLabel.innerHTML.replace(/[1-5]{1}/g, ""+tagCount);
        			tagCount++;
    	    	}
    			else if(type === "work"){
    				let workRow = child.children[0];
    				let tagLabel = workRow.children[0];
        			tagLabel.innerHTML = tagLabel.innerHTML.replace(/[1-5]{1}/g, ""+tagCount);
        			tagCount++;
    	    	}
    		}
    		console.log("child class list " + child.getAttribute("class"))
    	}	
    }
	
	const removeAcad = (acadIndex) => {
    	location.href = "/job-portal/RemoveCred?cred=acad&ind="+acadIndex;
    }
    const removeWork = (workIndex) => {
    	location.href = "/job-portal/RemoveCred?cred=work&ind="+workIndex;
    }
    const removeAward = (awardIndex) => {
    	location.href = "/job-portal/RemoveCred?cred=award&ind="+awardIndex;
    }
	
	const acadFormInputsEl = document.getElementById("acadFormInputs");
    const workExpFormInputsEl = document.getElementById("workExpFormInputs");
    const awardFormInputsEl = document.getElementById("awardFormInputs");
    const genericEl = document.getElementById("floatingAcademic1");
    const genericWidth = genericEl.style.width;
    
    const addNewAcad = function(){
    	let num = acadFormInputsEl.children.length;
    	if(num < 6){
    		const newDivEl = document.createElement("div");
            newDivEl.classList.add(["form-group", "cred-form-row"]);
            newDivEl.style.minWidth = "300px";
        	newDivEl.style.maxWidth = "700px";
        	newDivEl.style.margin = "8px 0px";
        	newDivEl.style.display = "flex";
        	newDivEl.style.flexDirection = "row";
        	newDivEl.style.justifyContent = "space-between";
        	newDivEl.style.alignItems = "center";
        	newDivEl.id = "acad-"+num;
            const newLabelEl = document.createElement("label");
            newLabelEl.for = "acad"+num;
            newLabelEl.classList.add("cred-form-label");
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
            const removeAcadButton = document.createElement("button");
            removeAcadButton.classList.add(["remove-cred-button"]);
            removeAcadButton.type = "button";
            removeAcadButton.onclick= function(){
            	acadFormInputsEl.removeChild(document.getElementById("acad-"+num));
            	renumberLabels("acadFormInputs", "acad");
            };
            const removeIcon = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            removeIcon.setAttribute("viewBox", "0 0 24 24");
            const useResource = document.createElementNS("http://www.w3.org/2000/svg", "use");
            useResource.setAttribute("href", "/job-portal/resources/fa/solid.svg#square-xmark");
            useResource.setAttribute("xlink:href", "/job-portal/resources/fa/solid.svg#square-xmark");
            removeIcon.append(useResource);
            removeAcadButton.append(removeIcon);
            newDivEl.appendChild(newLabelEl);
            newDivEl.appendChild(newSelectEl);
            newDivEl.appendChild(removeAcadButton);
            acadFormInputsEl.appendChild(newDivEl);
    	}else {
    		const maxAcadEntryMessageEl = document.getElementById("maxAcadEntryMessage");
    		maxAcadEntryMessageEl.style.display = "block";
    	}
    }
    
    const addNewWorkExp = () => {
    	let num = workExpFormInputsEl.children.length;
    	if(num < 6){
    		const newDivEl = document.createElement("div");
            newDivEl.classList.add(["form-group", "work-cred-form-group"]);
            newDivEl.style.minWidth = "300px";
        	newDivEl.style.maxWidth = "700px";
        	newDivEl.style.margin = "8px 0px";
        	newDivEl.style.display = "flex";
        	newDivEl.style.flexDirection = "column";
        	newDivEl.style.justifyContent = "start";
        	newDivEl.style.alignItems = "start";
        	newDivEl.id = "work-"+num;
            const newLabelEl = document.createElement("label");
            newLabelEl.for = "floatingWorkExp"+num;
            newLabelEl.classList.add("cred-form-label");
            newLabelEl.innerHTML = "Work Experience " + num;
            const newTextAreaEl = document.createElement("textarea");
            newTextAreaEl.name = "workExp"+num;
            newTextAreaEl.classList.add("form-control");
            newTextAreaEl.placeholder="Work Experience";
            newTextAreaEl.id="floatingWorkExp"+num;
            newTextAreaEl.maxLength = "255";
            const removeWorkButton = document.createElement("button");
            removeWorkButton.classList.add(["remove-work-button"]);
            removeWorkButton.type = "button";
            removeWorkButton.onclick= function(){
            	workExpFormInputsEl.removeChild(document.getElementById("work-"+num));
            	renumberLabels("workExpFormInputs", "work");
            };
            const removeIcon = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            removeIcon.setAttribute("viewBox", "0 0 18 18");
            const useResource = document.createElementNS("http://www.w3.org/2000/svg", "use");
            useResource.setAttribute("href", "/job-portal/resources/fa/solid.svg#square-xmark");
            useResource.setAttribute("xlink:href", "/job-portal/resources/fa/solid.svg#square-xmark");
            removeIcon.append(useResource);
            removeWorkButton.append(removeIcon);
            const newWorkRowDivEl = document.createElement("div");
            newWorkRowDivEl.classList.add(["work-cred-row"]);
            newWorkRowDivEl.style.marginTop = "8px";
            newWorkRowDivEl.style.marginBottom = "4px";
            newWorkRowDivEl.style.display = "flex";
            newWorkRowDivEl.style.alignItems = "center";
            newWorkRowDivEl.appendChild(newLabelEl);
            newWorkRowDivEl.appendChild(removeWorkButton);
            newDivEl.style.width = genericWidth;
            newDivEl.appendChild(newWorkRowDivEl);
            newDivEl.appendChild(newTextAreaEl);
            workExpFormInputsEl.appendChild(newDivEl);
    	}else {
    		const maxWorkEntryMessageEl = document.getElementById("maxWorkEntryMessage");
    		maxWorkEntryMessageEl.style.display = "block";
    	}
        
    }

    const addNewAward = () => {
    	let num = awardFormInputsEl.children.length;
    	if(Number(num) < 6){
    		const newDivEl = document.createElement("div");
            newDivEl.classList.add(["form-group", "cred-form-row"]);
            newDivEl.style.minWidth = "300px";
        	newDivEl.style.maxWidth = "700px";
        	newDivEl.style.margin = "8px 0px";
        	newDivEl.style.display = "flex";
        	newDivEl.style.flexDirection = "row";
        	newDivEl.style.justifyContent = "space-between";
        	newDivEl.style.alignItems = "center";
        	newDivEl.id="award-"+num;
            const newLabelEl = document.createElement("label");
            newLabelEl.for = "floatingAward"+num;
            newLabelEl.classList.add("cred-form-label");
            newLabelEl.innerHTML = "Award " + num;
            const newInputEl = document.createElement("input");
            newInputEl.type = "text";
            newInputEl.maxLength = "50";
            newInputEl.name = "award" + num;
            newInputEl.classList.add("form-control");
            newInputEl.placeholder = "Award";
            newInputEl.id = "floatingAward" + num;
            const removeAwardButton = document.createElement("button");
            removeAwardButton.classList.add(["remove-cred-button"]);
            removeAwardButton.type = "button";
            removeAwardButton.onclick= function(){
            	awardFormInputsEl.removeChild(document.getElementById("award-"+num));
            	renumberLabels("awardFormInputs", "award");
            };
            const removeIcon = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            removeIcon.setAttribute("viewBox", "0 0 24 24");
            const useResource = document.createElementNS("http://www.w3.org/2000/svg", "use");
            useResource.setAttribute("href", "/job-portal/resources/fa/solid.svg#square-xmark");
            useResource.setAttribute("xlink:href", "/job-portal/resources/fa/solid.svg#square-xmark");
            removeIcon.append(useResource);
            removeAwardButton.append(removeIcon);
            newDivEl.appendChild(newLabelEl);
            newDivEl.appendChild(newInputEl);
            newDivEl.appendChild(removeAwardButton);
            newDivEl.style.width = genericWidth;
            awardFormInputsEl.appendChild(newDivEl);
    	}else {
    		const maxAwardEntryMessageEl = document.getElementById("maxAwardEntryMessage");
    		maxAwardEntryMessageEl.style.display = "block";
    	}
    }
    
    
	</script>
</div>
