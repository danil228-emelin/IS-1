const backendUrl = 'http://localhost:8080/api/people';

document.addEventListener("DOMContentLoaded", function () {
    const historyTableBody = document.getElementById("historyTableBody");

    // Получаем историю
    fetch(`${backendUrl}/import/history`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            "Access-Control-Allow-Origin": "*"
        }
    })
        .then(response => response.json())
        .then(data => {
            data.forEach(history => {
                const row = document.createElement("tr");

                const idCell = document.createElement("td");
                idCell.textContent = history.id;
                row.appendChild(idCell);

                const statusCell = document.createElement("td");
                statusCell.textContent = history.status;
                row.appendChild(statusCell);

                const userCell = document.createElement("td");
                userCell.textContent = history.userName;
                row.appendChild(userCell);

                const addedCountCell = document.createElement("td");
                addedCountCell.textContent = history.status === 'SUCCESS' ? history.addedCount : 'N/A';
                row.appendChild(addedCountCell);

                const timestampCell = document.createElement("td");
                timestampCell.textContent = new Date(history.timestamp).toLocaleString();
                row.appendChild(timestampCell);

                const fileName = document.createElement("td");
                fileName.textContent = history.fileName;
                row.appendChild(fileName);

                historyTableBody.appendChild(row);
                console.log(history)
            });
        })
        .catch(error => {
            console.error('Error fetching import history:', error);
        });
});
