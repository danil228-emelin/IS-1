const backendUrl = 'http://localhost:8080/api/people';


function renderTable(data) {
    const content = data.content;  // This is an array of PersonDto objects
    const totalPages = data.totalPages;  // Number of pages in the result
    const totalElements = data.totalElements;  // Total number of items
    const currentPage = data.pageable.pageNumber;  // Current page number
    console.log("totalPages "+totalPages);
    console.log("totalElements "+totalElements);
    console.log("currentPage "+currentPage);
    // Get the table element where you want to display the data
    const tableBody = document.getElementById('tableBody');

    // Clear the previous table data (if any)
    tableBody.innerHTML = '';

    // Loop through the content (people) and add rows to the table
    content.forEach(person => {
        const row = document.createElement('tr');

        const cell1 = document.createElement('td');
        console.log(person)
        cell1.textContent = person.id;
        row.appendChild(cell1);

        const cell2 = document.createElement('td');
        cell2.textContent = person.name;
        row.appendChild(cell2);

        const cell3 = document.createElement('td');
        cell3.textContent = person.coordinates.coordinate_x;
        row.appendChild(cell3);

        const cell4 = document.createElement('td');
        cell4.textContent = person.coordinates.coordinate_y;
        row.appendChild(cell4);

        const cell5 = document.createElement('td');
        cell5.textContent = person.eye_color;
        row.appendChild(cell5);

        const cell6 = document.createElement('td');
        cell6.textContent = person.location.location_x;
        row.appendChild(cell6);

        const cell7 = document.createElement('td');
        cell7.textContent = person.location.location_y;
        row.appendChild(cell7);

        const cell8 = document.createElement('td');
        cell8.textContent = person.location.location_x;
        row.appendChild(cell8);

        const cell9 = document.createElement('td');
        cell9.textContent = person.weight;
        row.appendChild(cell9);

        const cell10 = document.createElement('td');
        cell10.textContent = person.nationality;
        row.appendChild(cell10)

        const cell11 = document.createElement('td');
        cell11.textContent = person.admin_edit_allowed;
        row.appendChild(cell11)

        // Append the row to the table body
        tableBody.appendChild(row);
    });

}

// Function to send a request on page load
async function sendRequestOnPageLoad() {
    // Retrieve the token from localStorage
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }
    try {
        // Make the request with the Authorization header
        const response = await fetch(`${backendUrl}`, {
            method: 'GET', // Adjust the HTTP method as needed
            headers: {
                'Authorization': `Bearer ${token}`,
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }

        // Parse the JSON response
        const data = await response.json();
        console.log('Response data:', data);

        renderTable(data);
        // Handle the response (e.g., update the UI)
    } catch (error) {
        console.error('Request failed:', error);
        alert('Failed to fetch data. Please try again.');
    }
}

// Execute the function on page load
window.onload = sendRequestOnPageLoad;

// Modal handling
const modal = document.getElementById("study_group_model");
const btn = document.getElementById("Create_study_group_btn");
const span = document.getElementsByClassName("close")[0];
const form = document.getElementById("study_group_model_form");
const tableBody = document.getElementById("tableBody");

btn.onclick = function() {
    modal.style.display = "block";
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}


form.onsubmit = function(event) {
    event.preventDefault(); // Prevent form submission
    const id = document.getElementById("id").value;
    const name = document.getElementById("name").value;
    const coord_x = document.getElementById("coord_x").value;
    const coord_y = document.getElementById("coord_y").value;
    const amount_of_students = document.getElementById("amount_of_students").value;
    const study_semester_enum = document.getElementById("study_semester_enum").value;
    const study_form_education = document.getElementById("study_form_education").value;

    const newRow = document.createElement("tr");
    const rowNumber = tableBody.rows.length + 1;

    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/group`, {
        method: 'POST',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            "name": name,
            "coordinates": {
                "coordinate_x": coord_x,
                "coordinate_y": coord_y
            },
            "study_students_count": amount_of_students,
            "study_semester_enum": study_semester_enum,
            "study_form_of_education": study_form_education
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal.style.display = "none";

}