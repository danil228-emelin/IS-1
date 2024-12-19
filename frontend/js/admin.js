const backendUrl = 'http://localhost:24727/api/admin';

tableBody = document.getElementById('tableBody');

function renderTable(data) {
    const content = data.content;  // This is an array of PersonDto objects
    const totalPages = data.totalPages;  // Number of pages in the result
    const totalElements = data.totalElements;  // Total number of items
    const currentPage = data.pageable.pageNumber;  // Current page number
    console.log("totalPages " + totalPages);
    console.log("totalElements " + totalElements);
    console.log("currentPage " + currentPage);
    // Get the table element where you want to display the data

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
        cell2.textContent = person.username;
        row.appendChild(cell2);
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
        const response = await fetch(`${backendUrl}/registration-requests`, {
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


const modal = document.getElementById("Approve-modal");
const btn = document.getElementById("approve-btn");
const form = document.getElementById("idForm");

btn.onclick = function () {
    modal.style.display = "block";
}



window.onclick = function (event) {
    if (event.target ==modal) {
        modal.style.display = "none";
    }
}

form.onsubmit = function (event) {
    event.preventDefault(); // Prevent form submission

    const id = document.getElementById("idInput").value;
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/registration-requests/${id}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({})
    })
        .then(response => response.json())
        .then(data => {
            location.reload();
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal.style.display = "none";
}


const modal_delete = document.getElementById("Delete-modal");
const btn_delete = document.getElementById("disapprove-btn");
const form_delete = document.getElementById("idForm");

btn_delete.onclick = function () {
    modal_delete.style.display = "block";
}



window.onclick = function (event) {
    if (event.target ==modal_delete) {
        modal_delete.style.display = "none";
    }
}

form_delete.onsubmit = function (event) {
    event.preventDefault(); // Prevent form submission

    const id = document.getElementById("idInput").value;
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage');
        alert('You must log in first!');
        // Optionally, redirect to the login page
        window.location.href = '../index.html';
        return;
    }

    fetch(`${backendUrl}/registration-requests/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({})
    })
        .then(response => response.json())
        .then(() => {
            location.reload();
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    modal_delete.style.display = "none";
}

function closePopup() {
    document.getElementById('Approve-modal').style.display = 'none';
}

// Function to close the Delete Popup
function closeDeletePopup() {
    document.getElementById('Delete-modal').style.display = 'none';
}