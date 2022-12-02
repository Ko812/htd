console.log("validation file encoding: "+ document.characterSet);
const digits = "0123456789";
const capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
const smallLetters = "abcdefghijklmnopqrstuvwxyz";
const validNRICInitialChars = "STFGM";


/****************************************
	Overall form validation.
Call this to add the event listener to
intercept any form submission when any of the
inputs provided did not pass the validators
checks.
*****************************************/

const ensureValidation = function(formId, validators, inputTags) {
	let formElement = document.getElementById(formId);
	if (validators.length != inputTags.length) {
		throw "Validators and inputs must be the same length.";
	}
	formElement.addEventListener("submit", (event) => {
		event.cancelable = true;
		let input;
		for (let i = 0; i < validators.length; i++) {
			input = document.getElementById(inputTags[i]).value;
			if (!validators[i](input)) {
				event.preventDefault();
				alert("You have invalid entries in the form.");
				return;
			}
		}
	});
}

/**************************************
		Password validator
***************************************/


const hasDigit = function(str) {
	const digits = "0123456789";
	for (let i = 0; i < digits.length; i++) {
		if (str.includes(digits[i])) {
			return true;
		}
	}
	return false;
}

const hasCapitalLetter = function(str) {

	for (let i = 0; i < capitalLetters.length; i++) {
		if (str.includes(capitalLetters[i])) {
			return true;
		}
	}
	return false;
}

const hasSmallLetter = function(str) {

	for (let i = 0; i < smallLetters.length; i++) {
		if (str.includes(smallLetters[i])) {
			return true;
		}
	}
	return false;
}

const hasSpecialChar = function(str) {
	const specialChar = "!@#$%^&*()-_+=,<.>/?`~{[}]|\\";
	for (let i = 0; i < specialChar.length; i++) {
		if (str.includes(specialChar[i])) {
			return true;
		}
	}
	return false;
}

const validLength = function(str) {
	return str.length <= 24 && str.length >= 8;
}

const validPassword = function(pwd) {
	return validLength(pwd) && hasSmallLetter(pwd) && hasCapitalLetter(pwd) && hasDigit(pwd);
}

const validPasswordWithSpecialChar = function(pwd) {
	return validLength(pwd) && hasSmallLetter(pwd) && hasCapitalLetter(pwd) && hasDigit(pwd) && hasSpecialChar(pwd);
}

/**************************************
	Email validator	
 **************************************/

const validEmail = function(email) {
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
		return true;
	}
	return false;
}

/**************************************
	Name validator	
 **************************************/

const nameValidator = (name) => {
	// Do not contain digits
	for (let d of digits) {
		if (name.includes(d)) {
			return false;
		}
	}
	return true;
}

/*************************************
		Contact number validator
 *************************************/

const contactValidator = function(contact) {
	if (contact.length != 8) {
		return false;
	}
	for (let c of contact) {
		if (!digits.includes(c)) {
			return false;
		}
	}
	console.log("verified contact " + contact);
	return true;
}

/*************************************
		NRIC Validators
**************************************/

const nricChecksum = (numStr, init) => {
	const sum = 2 * Number(numStr[0]) + 7 * Number(numStr[1]) + 6 * Number(numStr[2]) + 5 * Number(numStr[3]) + 4 * Number(numStr[4]) + 3 * Number(numStr[5]) + 2 * Number(numStr[6]);
	if (init == "T" || init == "G") {
		sum = sum + 4;
	}
	const r = sum % 11;
	if (init == "S" || init == "T") {
		switch (r) {
			case 10:
				return "A";
			case 9:
				return "B";
			case 8:
				return "C";
			case 7:
				return "D";
			case 6:
				return "E";
			case 5:
				return "F";
			case 4:
				return "G";
			case 3:
				return "H";
			case 2:
				return "I";
			case 1:
				return "Z";
			case 0:
				return "J";
			default:
				return "-";
		}
	}
	else if (init == "F" || init == "G") {
		switch (r) {
			case 10:
				return "K";
			case 9:
				return "L";
			case 8:
				return "M";
			case 7:
				return "N";
			case 6:
				return "P";
			case 5:
				return "Q";
			case 4:
				return "R";
			case 3:
				return "T";
			case 2:
				return "U";
			case 1:
				return "W";
			case 0:
				return "X";
			default:
				return "-";
		}
	}
	return "-";
}

const NRICValidator = function(nric) {
	nric = nric.toUpperCase();
	const firstChar = nric[0];
	const lastChar = nric[nric.length - 1];
	if (!validNRICInitialChars.includes(firstChar)) {
		return false;
	}
	if (nric.length != 9) {
		return false;
	}
	if (!capitalLetters.includes(lastChar)) {
		return false;
	}
	const numericPart = nric.substring(1, 8);
	if (nricChecksum(numericPart, firstChar) != lastChar) {
		return false;
	}
	return true;
}

const isNumeric = function(str){
	if(str.length == 0){
		return false;
	}
	else {
		for(let i = 0;i < str.length; i++){
			if(!digits.includes(str[i])){
				return false;
			}
		}
		return true;
	}
}

const normalTextAdditionalChars = " -,'\"";

const isAngloText = function(str){
	const acceptedChars = capitalLetters + smallLetters + normalTextAdditionalChars;
	for(let i = 0; i < str.length;i++){
		if(!acceptedChars.includes(str[i])){
			return false;
		}
	}
	return true;
}

const isAngloTextWithDigits = function(str){
	const acceptedChars = capitalLetters + smallLetters + digits + normalTextAdditionalChars;
	for(let i = 0; i < str.length;i++){
		if(!acceptedChars.includes(str[i])){
			return false;
		}
	}
	return true;
}

const frenchLetters = "àâçéèëêìîïôòûüùÀÂÇÉÈËÊÌÎÏÔÒÛÜÙ";

const isAngloFrancoText = function(str){
	const acceptedChars = capitalLetters + smallLetters + normalTextAdditionalChars + frenchLetters;
	for(let i = 0; i < str.length;i++){
		if(!acceptedChars.includes(str[i])){
			return false;
		}
	}
	return true;
}