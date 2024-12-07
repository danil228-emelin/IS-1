const backendUrl = 'http://localhost:8080/api/groups';


function renderTable(data) {
    const content = data.content;  // This is an array of PersonDto objects
    const totalPages = data.totalPages;  // Number of pages in the result
    const totalElements = data.totalElements;  // Total number of items
    const currentPage = data.pageable.pageNumber;  // Current page number
    console.log("totalPages " + totalPages);
    console.log("totalElements " + totalElements);
    console.log("currentPage " + currentPage);
    // Get the table element where you want to display the data
    const tableBody = document.getElementById('tableBody');

    // Clear the previous table data (if any)
    tableBody.innerHTML = '';

    // Loop through the content (people) and add rows to the table
    content.forEach(group => {
        const row = document.createElement('tr');

        const cell1 = document.createElement('td');
        console.log(group)
        cell1.textContent = group.id;
        row.appendChild(cell1);

        const cell2 = document.createElement('td');
        cell2.textContent = group.name;
        row.appendChild(cell2);

        const cell3 = document.createElement('td');
        cell3.textContent = group.coordinates.coordinate_x;
        row.appendChild(cell3);

        const cell4 = document.createElement('td');
        cell4.textContent = group.coordinates.coordinate_y;
        row.appendChild(cell4);


        const cell5 = document.createElement('td');
        cell5.textContent = group.students_count;
        row.appendChild(cell5);

        const cell6 = document.createElement('td');
        cell6.textContent = group.form_of_education;
        row.appendChild(cell6)

        const cell7 = document.createElement('td');
        cell7.textContent = group.average_mark;
        row.appendChild(cell7)
        cell8 = ""
        if (group.hasOwnProperty('admin') && group.admin !== null) {
            cell8 = document.createElement('td');
            cell8.textContent = group.admin;
        } else {
            cell8 = document.createElement('td');
            cell8.textContent = "No admin";
        }
        row.appendChild(cell8)

        const cell9 = document.createElement('td');
        cell9.textContent = group.semester_enum;
        row.appendChild(cell9)

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
        const response = await fetch(`${backendUrl}/getAllGroups`, {
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

btn.onclick = function () {
    modal.style.display = "block";
}

span.onclick = function () {
    modal.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}


form.onsubmit = function (event) {
    event.preventDefault(); // Prevent form submission
    const name = document.getElementById("name").value;
    const coord_x = document.getElementById("coord_x").value;
    const coord_y = document.getElementById("coord_y").value;
    const amount_of_students = document.getElementById("amount_of_students").value;
    const average = document.getElementById("average_mark").value;
    const study_semester_enum = document.getElementById("study_semester_enum").value;
    const study_form_education = document.getElementById("study_form_education").value;

    const newRow = document.createElement("tr");
    const rowNumber = tableBody.rows.length + 1;
    const admin = document.getElementById("admin_id").value;
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/create`, {
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
            "students_count": amount_of_students,
            "admin": admin,
            "average_mark": average,
            "semester_enum": study_semester_enum,
            "form_of_education": study_form_education
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            const newRow = document.createElement("tr");
            if (data.hasOwnProperty('admin') && data.study_id !== null) {
                newRow.innerHTML = `
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.coordinates.coordinate_x}</td>
                <td>${data.coordinates.coordinate_y}</td>                
                <td>${data.students_count}</td>
                <td>${data.semester_enum}</td>
                <td>${data.average_mark}</td>
                <td>${data.admin}</td>
                <td>${data.form_of_education}</td>
            `;
            } else {
                newRow.innerHTML = `
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.coordinates.coordinate_x}</td>
                <td>${data.coordinates.coordinate_y}</td>                
                <td>${data.students_count}</td>
                <td>${data.semester_enum}</td>
                <td>${data.average_mark}</td>
                <td>"No admin"</td>
                <td>${data.form_of_education}</td>
            `;
            }
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

// Modal handling
const modal_person = document.getElementById("add_person_model");
const btn_person = document.getElementById("Add_person_in_group_btn");
const form_person = document.getElementById("add_person_form");

btn_person.onclick = function () {
    modal_person.style.display = "block";
}

span.onclick = function () {
    modal_person.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal_person) {
        modal_person.style.display = "none";
    }
}


form_person.onsubmit = function (event) {
    event.preventDefault(); // Prevent form submission
    const person_id = document.getElementById("person_id").value;
    const group_id = document.getElementById("group_id").value;

    const newRow = document.createElement("tr");

    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/addPerson`, {
        method: 'POST',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            "group_id": group_id,
            "person_id": person_id
        })
    })
        .then(response => response.json())
        .then(()=> {
            alert("Student added in group successfully!");
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal_person.style.display = "none";
}


// Modal handling
const modal_average = document.getElementById("average_mark_model");
const btn_average = document.getElementById("averageMarkBtn");
const form_average = document.getElementById("average_mark_form");

btn_average.onclick = function () {
    modal_average.style.display = "block";
}

span.onclick = function () {
    modal_average.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal_average) {
        modal_average.style.display = "none";
    }
}


form_average.onsubmit = function (event) {
    event.preventDefault(); // Prevent form submission
    const average = document.getElementById("average_mark_less").value;


    const newRow = document.createElement("tr");

    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/count-by-average-mark?averageMark=${average}`, {
        method: 'GET',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert("Amount of groups   " + data.count)
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal_average.style.display = "none";
}


const btn_min = document.getElementById("minGroupAdminBtn");


btn_min.onclick = function (event) {
    event.preventDefault(); // Prevent form submission

    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/getMinimalGroupId`, {
        method: 'GET',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            // Create the table HTML
            let info = `Admin with minimal id   ${data.id}\n`;
            alert(info)
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal_average.style.display = "none";
}


// Modal handling
const modal_admin = document.getElementById("moreId_mark_model");
const btn_admin = document.getElementById("moreThanAdminIdBtn");
const form_admin = document.getElementById("moreId_mark_form");

btn_admin.onclick = function () {
    modal_admin.style.display = "block";
}

span.onclick = function () {
    modal_admin.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal_admin) {
        modal_admin.style.display = "none";
    }
}


form_admin.onsubmit = function (event) {
    event.preventDefault(); // Prevent form submission
    const average = document.getElementById("moreId_mark_less").value;

    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/count-by-admin-id?groupId=${average}`, {
        method: 'GET',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert("Amount of groups   " + data.count)
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal_average.style.display = "none";
}


// Modal handling
const modal_delete = document.getElementById("delete_model");
const btn_delete = document.getElementById("deleteAll_btn");
const form_delete = document.getElementById("delete_form");

btn_delete.onclick = function () {
    modal_delete.style.display = "block";
}

span.onclick = function () {
    modal_delete.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal_delete) {
        modal_delete.style.display = "none";
    }
}


form_delete.onsubmit = function (event) {
    event.preventDefault(); // Prevent form submission
    const group_id = document.getElementById("group_id_delete").value;

    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/delete-all?groupId=${group_id}`, {
        method: 'DELETE',
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert("Amount of groups   " + data.count)
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal_delete.style.display = "none";
}