const REFRESH = 1; // Time to refresh the leaderboard (1 AM)
let cachedData = [];

function formatNumber(number) {
    return new Intl.NumberFormat('en-US', { maximumFractionDigits: 0 }).format(number);
}

// Function to fetch the JSON data
async function getData() {
    try {
        const response = await fetch("https://thegambizz.com/api/razed");
        if (!response.ok) {
            throw new Error(`HTTP Error! Status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error fetching data: ', error);
        return null;
    }
}

// Fetch the top 10 users
async function getTop10() {
    const data = await getData();

    // Assuming 'data' is an array or object with wagerAmount
    return Object.entries(data)
        .map(([key, value]) => ({ ...value, key })) // Map to keep key if needed
        .sort((a, b) => b.wagered - a.wagered)
        .slice(0, 10);
}

// Function to update the leaderboard
async function updateLeaderboard() {
    if (cachedData.length === 0) {
        cachedData = await getTop10(); // Fetch data if cached is empty
    }

    if (cachedData.length === 0) {
        console.log("Error while updating leaderboard!");
        return;
    }

    // Update the leaderboard table and podium
    if (document.querySelector('.leaderboard-table tbody') !== null) {
        updateLeaderboardTable(cachedData);
    }
    updatePodium(cachedData.slice(0, 3)); // Top 3 for the podium
}

function anonymizeName(name) {
    const length = name.length;

    // If the name has 3 or fewer characters, return it as is
    if (length <= 3) {
        return name;
    }

    // Calculate the starting index for the 2 middle characters
    const start = Math.floor((length - 1) / 2) - (length % 2 === 0 ? 1 : 0);

    // Replace the 2 middle characters with '*'
    const censoredName =
        name.slice(0, start) + // Characters before the middle
        '**' + // Replace 2 middle characters
        name.slice(start + 2); // Characters after the middle

    return censoredName;
}

function getPriceByIndex(index) {
    const prices = [200, 150, 125, 75, 50, 25, 25];

    if (index < 0 || index >= prices.length) {
        throw new Error("Index out of bounds");
    }

    return prices[index];
}

// Update the leaderboard table
function updateLeaderboardTable(users) {
    const tbody = document.querySelector('.leaderboard-table tbody');
    tbody.innerHTML = ''; // Clear existing rows

    for (let i = 3; i < 10; i++) {
        const user = users[i];
        const rank = i + 1;
        const prize = rank > 3 ? getPriceByIndex(rank - 4) : 0;

        const row = document.createElement('tr');

        if (user) {
            row.innerHTML = `
                <td>#${rank}</td>
                <td>${anonymizeName(user.username)}</td>
                <td><span class="highlight">$</span> ${formatNumber(user.wagered)}</td>
                <td>${rank > 3 ? `<span class="highlight">$</span> ${prize}` : ''}</td>
            `;
        } else {
            row.innerHTML = `
                <td>#${rank}</td>
                <td>N/A</td>
                <td><span class="highlight">$</span> 0.00</td>
                <td>${rank > 3 ? `<span class="highlight">$</span> ${prize}` : ''}</td>
            `;
        }

        tbody.appendChild(row);
    }
}

// Update podium display
function updatePodium(topUsers) {
    topUsers.forEach((user, index) => {
        const podiumBox = document.getElementById(`${index + 1}`);
        if (user == null) {
            podiumBox.querySelector('.name').textContent = "N/A";
            podiumBox.querySelector('.wagered-amount h4').innerHTML = `<span class="highlight">$ </span>0.00`;
        } else {
            if (podiumBox) {
                podiumBox.querySelector('.name').textContent = anonymizeName(user.username);
                podiumBox.querySelector('.wagered-amount').innerHTML = `<span class="highlight">$ </span>${formatNumber(user.wagered)}`;
            }
        }
    });
}

// Timer to refresh data at 1 AM
async function updateTimer() {
    const now = new Date();
    const targetTime = new Date();

    if (now.getHours() >= 1) {
        targetTime.setDate(targetTime.getDate() + 1);
    }

    targetTime.setHours(REFRESH, 0, 0, 0);
    const timeRemaining = targetTime - now;

    if (timeRemaining <= 0) {
        cachedData = []; // Clear cached data
        await updateLeaderboard(); // Fetch new data and update the leaderboard
        console.log("Data refreshed!");
    }
}

// Set interval to check for updates every second
setInterval(updateTimer, 1000);

// Initialize the leaderboard on page load
document.addEventListener('DOMContentLoaded', async () => {
    await updateLeaderboard();
});
