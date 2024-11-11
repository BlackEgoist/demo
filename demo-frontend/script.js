const BACKEND_API_BASE_URL = 'http://localhost:8080';

// Utility function to show or hide forms
function displayForm(formId) {
    document.getElementById('register-form').style.display = 'none';
    document.getElementById('login-form').style.display = 'none';
    document.getElementById('task-form').style.display = 'none';
    document.getElementById(formId).style.display = 'block';
}

// checking if user is already logged in
function checkSession() {
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser) {
        document.getElementById('current-user').textContent = currentUser;
        displayForm('task-form');
    } else {
        displayForm('login-form');
    }
}

function goToRegisterForm() {
    displayForm('register-form');
}

function goToLoginForm() {
    displayForm('login-form');
}

// register new user
async function registerUser() {
    const username = document.getElementById('register-username').value;
    const password = document.getElementById('register-password').value;

    const response = await fetch(`${BACKEND_API_BASE_URL}/users`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });

    if (response.status === 201) {
        document.getElementById('register-message').textContent = 'Registration successful! Please login.';
        displayForm('login-form');
    } else {
        document.getElementById('register-message').textContent = 'Username already exists.';
    }
}

// login
async function loginUser() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    let auth = btoa(`${username}:${password}`);

    const response = await fetch(`${BACKEND_API_BASE_URL}/users/me`, {
        method: 'GET',
        headers: { 'Authorization': `Basic ${auth}` },
    });

    if (response.ok) {
        localStorage.setItem('currentUser', username);
        localStorage.setItem('currentPassword', password);
        document.getElementById('login-message').textContent = 'Logged in';
        document.getElementById('current-user').textContent = username;
        displayForm('task-form');
    } else {
        document.getElementById('login-message').textContent = 'Invalid username or password';
    }
}

// Create a new task
async function createTask() {
    const author = localStorage.getItem('currentUser');
    const recipient = document.getElementById('task-recipient').value;
    const text = document.getElementById('task-text').value;

    let auth = btoa(`${localStorage.getItem('currentUser')}:${localStorage.getItem('currentPassword')}`);

    const response = await fetch(`${BACKEND_API_BASE_URL}/tasks`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json',
                    'Authorization': `Basic ${auth}` ,
                },
        body: JSON.stringify({ recipientUsername: recipient, text })
    });

    if (response.ok) {
        document.getElementById('task-message').textContent = 'Task created';
    } else {
        document.getElementById('task-message').textContent = 'Failed to create task';
    }
}

// Logout user
function logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentPassword');
    displayForm('login-form');
    document.getElementById('login-message').textContent = 'Logged out';
}

document.addEventListener('DOMContentLoaded', checkSession);
