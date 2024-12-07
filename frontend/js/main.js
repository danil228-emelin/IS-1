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
        cell8.textContent = person.location.location_z;
        row.appendChild(cell8);

        const cell9 = document.createElement('td');
        cell9.textContent = person.weight;
        row.appendChild(cell9);

        const cell10 = document.createElement('td');
        cell10.textContent = person.study_id.id;
        row.appendChild(cell10)

        const cell11 = document.createElement('td');
        cell11.textContent = person.nationality;
        row.appendChild(cell11)

        const cell12 = document.createElement('td');
        cell12.textContent = person.admin_edit_allowed;
        row.appendChild(cell12)

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
const modal = document.getElementById("addPersonModal");
const btn = document.getElementById("addPersonBtn");
const span = document.getElementsByClassName("close")[0];
const form = document.getElementById("addPersonForm");
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


const modal_update = document.getElementById("updatePersonModal");
const btn_update = document.getElementById("updatePersonBtn");
const span_update = document.getElementsByClassName("close")[0];
const form_update = document.getElementById("updatePersonForm");

btn_update.onclick = function() {
    modal_update.style.display = "block";
}

span_update.onclick = function() {
    modal_update.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal_update) {
        modal_update.style.display = "none";
    }
}


const modal_delete = document.getElementById("deletePersonModal");
const btn_delete = document.getElementById("deletePersonBtn");
const form_delete = document.getElementById("deletePersonForm");

btn_delete.onclick = function() {
    modal_delete.style.display = "block";
}

span_update.onclick = function() {
    modal_delete.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal_delete) {
        modal_delete.style.display = "none";
    }
}


form.onsubmit = function(event) {
    event.preventDefault(); // Prevent form submission

    const name = document.getElementById("name").value;
    const coord_x = document.getElementById("coord_x").value;
    const coord_y = document.getElementById("coord_y").value;
    const eye_color = document.getElementById("eye_color").value;
    const loc_x = document.getElementById("loc_x").value;
    const loc_y = document.getElementById("loc_y").value;
    const loc_z = document.getElementById("loc_z").value;
    const weight = document.getElementById("weight").value;
    const study_group = document.getElementById("study_id").value;
    const nationality = document.getElementById("nationality").value;
    const admin_allowed = document.getElementById("admin_allowed").value;

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

    fetch(`${backendUrl}`, {
        method: 'POST',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            "study_id":study_group,
            "name": name,
            "coordinates": {
                "coordinate_x": coord_x,
                "coordinate_y": coord_y
            },
            "eye_color": eye_color,
            "location": {
                "location_x": loc_x ,
                "location_y": loc_y,
                "location_z": loc_z
            },
            "weight": weight,
            "study_group":study_group,
            "nationality": nationality,
            "admin_edit_allowed": admin_allowed === true
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            const newRow = document.createElement("tr");
            // Populate the new row with the data (e.g., name, students_count, average_mark, etc.)
            newRow.innerHTML = `
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.coordinates.coordinate_x}</td>
                <td>${data.coordinates.coordinate_y}</td>                
                <td>${data.eye_color}</td>
                <td>${data.location.location_x}</td>
                <td>${data.location.location_y}</td>
                <td>${data.location.location_z}</td>
                <td>${data.weight}</td>
                <td>${data.study_id.id}</td>
                <td>${data.nationality}</td>
                <td>${data.admin_edit_allowed}</td>
            `;
            tableBody.appendChild(newRow);
            // Optionally clear the form after submission
            form.reset();
            // Optionally, show a success message or modal
            alert("Study group created successfully!");
        })
        .catch((error) => {
            console.error('Error:', error);
        });

modal.style.display = "none";

}


form_update.onsubmit = function(event) {
    event.preventDefault(); // Prevent form submission
    const id = document.getElementById("update_id").value;

    const name = document.getElementById("update_name").value;
    const coord_x = document.getElementById("update_coord_x").value;
    const coord_y = document.getElementById("update_coord_y").value;
    const eye_color = document.getElementById("update_eye_color").value;
    const loc_x = document.getElementById("update_loc_x").value;
    const loc_y = document.getElementById("update_loc_y").value;
    const loc_z = document.getElementById("update_loc_z").value;
    const weight = document.getElementById("update_weight").value;
    const nationality = document.getElementById("update_nationality").value;
    const admin_allowed = document.getElementById("update_admin_allowed").value;
    const study_group = document.getElementById("update_study_id").value;

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

    fetch(`${backendUrl}/${id}`, {
        method: 'PUT',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            "study_id":"300",
            "name": name,
            "coordinates": {
                "coordinate_x": coord_x,
                "coordinate_y": coord_y
            },
            "eye_color": eye_color,
            "location": {
                "location_x": loc_x ,
                "location_y": loc_y,
                "location_z": loc_z
            },
            "weight": weight,
            "study_group":study_group,
            "nationality": nationality,
            "admin_edit_allowed": admin_allowed === true,
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal_update.style.display = "none";

}

form_delete.onsubmit = function(event) {
    event.preventDefault(); // Prevent form submission
    const id = document.getElementById("update_id").value;

    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/${id}`, {
        method: 'DELETE',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
        body: {}
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal_delete.style.display = "none";

}
