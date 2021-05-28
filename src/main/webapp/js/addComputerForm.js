let nameInput = document.getElementById("computerName");
let introducedInput = document.getElementById("introduced");
let discontinuedInput = document.getElementById("discontinued");
let submitBtn = document.getElementById("submitBtn");
let nameValidated = false;
let introducedValidated = false;
let discotinuedValidated = false;

nameInput.addEventListener('input', function(evt){
   checkEmptyName();
   validateForm();
});

introducedInput.addEventListener('change', function(evt) {
    checkEmptyIntroducedDate();
    if (!isEmptyInput(introducedInput.value) && !isEmptyInput(discontinuedInput.value)) {
        checkIntroDiscDate();
    }
    validateForm();
});

discontinuedInput.addEventListener('change', function(evt) {
    if (!isEmptyInput(discontinuedInput.value)) {
        checkEmptyIntroducedDate();
        
        if (!isEmptyInput(introducedInput.value)) {
            checkIntroDiscDate();
        }
    }
    validateForm();
});

function checkIntroDiscDate() {
    let introDate = new Date(introducedInput.value);
    let discontiDate = new Date(discontinuedInput.value);
    if (discontiDate < introDate) {
        discontinuedInput.closest("div").classList.add("has-error");
        discontinuedInput.nextElementSibling.classList.remove("hidden");
        introducedValidated = false;
        discotinuedValidated = false;
    } else {
        discontinuedInput.closest("div").classList.remove("has-error");
        discontinuedInput.nextElementSibling.classList.add("hidden");
        introducedValidated = true;
        discotinuedValidated = true;
    }
   
}

function checkEmptyIntroducedDate() {
    if (isEmptyInput(introducedInput.value)) {
        introducedInput.closest("div").classList.add("has-error");
        introducedInput.nextElementSibling.classList.remove("hidden");
        introducedValidated = false;
    } else {
        introducedInput.closest("div").classList.remove("has-error");
        introducedInput.nextElementSibling.classList.add("hidden");
        introducedValidated = true;
    }
}

function checkEmptyName() {
    if (isEmptyInput(nameInput.value)) {
        nameInput.closest("div").classList.add("has-error");
        nameInput.nextElementSibling.classList.remove("hidden");
        nameValidated = false;
    } else {
        nameInput.closest("div").classList.remove("has-error");
        nameInput.nextElementSibling.classList.add("hidden");
        nameValidated = true;
    }
}

function validateForm() {
    if (nameValidated && introducedValidated && discotinuedValidated) {
        submitBtn.classList.remove("disabled");
    } else {
        submitBtn.classList.add("disabled");
    }
}
 
function isEmptyInput(str) {
    if (str.length === 0 || !str.trim()) {
        return true;
    }
}