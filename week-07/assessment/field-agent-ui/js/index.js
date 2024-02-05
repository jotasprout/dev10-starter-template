const DISPLAY_NONE = "d-none";

const form = document.querySelector("form");
let currentView = "landing";

function changeView(view) {
    for (const element of document.querySelectorAll(`.${currentView}`)) {
        element.classList.add(DISPLAY_NONE);
    }
    for (const element of document.querySelectorAll(`.${view}`)) {
        element.classList.remove(DISPLAY_NONE);
    }
    currentView = view;
}

// TODO: Populate an existing agent into the HTML form.
function showUpdate() {
    alert("Implement update!");
}

// TODO: Populate an existing agent into a delete confirmation view. 
// The confirmation view should allow for a delete or cancel.
// Cancel returns to the agent list view.
function confirmDelete(agentId) {
    alert(`Implement delete! Agent ID: ${agentId}`);
}

// TODO: create a function that deletes an agent when the
// delete confirmation view is confirmed. Confirmation can be a form submission
// or a button click.

function populateAgents(agents) {

    const table = document.querySelector(".list > table");
    const tbody = table.querySelector("tbody");
    const warning = document.querySelector(".list > div");

    if (agents.length === 0) {
        table.classList.add(DISPLAY_NONE);
        warning.classList.remove(DISPLAY_NONE);
        return;
    }

    let html = "";
    for (const agent of agents) {
        // TODO: This embedded HTML explicitly attaches a function call for update and delete.
        // Complete the confirmDelete and showUpdate functions.
        html += `<tr>
            <td>${agent.firstName}${agent.middleName ? " " + agent.middleName : ""} ${agent.lastName}</td>
            <td>${agent.dob}</td>
            <td>${agent.heightInInches}</td>
            <td>
                <button type="button" class="btn btn-danger me-2" onClick="confirmDelete(${agent.agentId})">Delete</button>
                <button type="button" class="btn btn-info" onClick="showUpdate()">Edit</button>
            </td>
        </tr>`;
    }

    tbody.innerHTML = html;
}

function fetchAgents() {
    fetch("http://localhost:8080/api/agent")
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            return Promise.reject();
        })
        .then(agents => populateAgents(agents))
        .catch(console.error);
}

function showList() {
    fetchAgents();
    changeView("list");
}

function showValidationSummary(errors) {
    let html = '<ul class="mb-0">';
    for (const err of errors) {
        html += `<li>${err}</li>`;
    }
    html += '</ul>';
    const validationSummary = document.getElementById("validationSummary");
    validationSummary.classList.remove(DISPLAY_NONE);
    validationSummary.innerHTML = html;
}

function hideValidationSummary() {
    document.getElementById("validationSummary").classList.add(DISPLAY_NONE);
}

// TODO: Modify this function to allow for update.
// Don't create two different forms for create and update.
function submitForm(evt) {

    evt.preventDefault();
    evt.stopPropagation();
    hideValidationSummary();

    if (form.checkValidity()) {

        const agent = {
            firstName: document.getElementById("firstName").value,
            middleName: document.getElementById("middleName").value,
            lastName: document.getElementById("lastName").value,
            dob: document.getElementById("dob").value,
            heightInInches: document.getElementById("heightInInches").value
        };

        const config = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(agent)
        };

        fetch("http://localhost:8080/api/agent", config)
            .then(response => {
                if (response.ok) {
                    showList();
                } else {
                    return response.json();
                }
            })
            .then(errors => {
                if (errors) {
                    showValidationSummary(errors);
                }
            })
            .catch(console.error);
    }
}

// event handlers

document.getElementById("linkAgents")
    .addEventListener("click", evt => {
        evt.preventDefault();
        showList();
    });

document.getElementById("linkAgencies")
    .addEventListener("click", evt => {
        evt.preventDefault();
    });

document.querySelector(".list button")
    .addEventListener("click", () => {
        changeView("form");
    });

form.addEventListener("submit", submitForm);

document.querySelector("form button[type=button]")
    .addEventListener("click", () => {
        showList();
    });