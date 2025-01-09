// NEED TO CHANGE WHEN GOING TO HELIOS.
const backendUrl = 'http://localhost:8080/api';

// Switch between login and register forms
document.getElementById('switchToRegister').addEventListener('click', function () {
    document.getElementById('loginForm').reset();
    document.getElementById('loginContainer').style.display = 'none';
    document.getElementById('registerContainer').style.display = 'block';
});

document.getElementById('switchToLogin').addEventListener('click', function () {
    document.getElementById('registerForm').reset();
    document.getElementById('registerContainer').style.display = 'none';
    document.getElementById('loginContainer').style.display = 'block';
});

// Handle Login form submission
document.getElementById('loginForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    if (password.length < 8) {
        alert('Password must be at least 8 characters long!');
        return; // Stop form submission
    }

    try {
        const response = await fetch(`${backendUrl}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: email,
                password: password,
            }),
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem('token', data.access_token); // Save the token in localStorage
            window.location.href = "html/main.html"
        } else {
            alert(data.message || 'Login failed');
        }
    } catch (error) {
        console.error('Error during login:', error);
    }
});

// Handle Register form submission
document.getElementById('registerForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    const isAdmin = document.getElementById("isAdmin").checked;
    if (password.length < 8) {
        alert('Password must be at least 8 characters long!');
        return; // Stop form submission
    }
    let url = ""
    if (isAdmin) {
        url = `${backendUrl}/auth/register-admin`
    }else{
        url = `${backendUrl}/auth/register`
    }
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: email,
                password: password
            }),
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem('token', data.access_token); // Save the token in localStorage
            window.location.href = "html/main.html"
        } else {
            alert(data.message || 'Registration failed');
        }
    } catch (error) {
        console.error('Error during registration:', error);
    }
});
