// Step 1: Fetch the JSON Data
async function getData() {
    try {
        const response = await fetch("https://thegambizz.com/api/data");
        if (!response.ok) {
            throw new Error(`HTTP Error! Status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error fetching data: ', error);
        return null;
    }
}

async function updateLeaderboard() {
    const data = await getData();

    if (!data) {
        console.error('No data received');
        return;
    }

    const date = new Date();
    const currentMonth = date.getMonth() + 1

    // Calculate total wager per user
    const userWagers = {};

    data.forEach(entry => {
        const month = entry.reportingDay.split('-')[1];
        if (month !== "0" + currentMonth) {
            return;
        }

        const username = entry.username || 'Unknown';  // Fallback if username is missing

        const wager = parseFloat(entry.bets) || 0;      // Convert to number and handle non-numeric values

        if (userWagers[username]) {
            userWagers[username] += wager;
        } else {
            userWagers[username] = wager;
        }
    });

    // Convert to an array and sort by wager amount
    const sortedUsers = Object.entries(userWagers)
        .map(([username, wager]) => ({ username, wager: parseFloat(wager.toFixed(2)) }))  // Round here and ensure it's a number
        .sort((a, b) => b.wager - a.wager);

    // Update the leaderboard
    if (document.querySelector('.leaderboard-table tbody') !== null) {
        updateLeaderboardTable(sortedUsers);
    }
    updatePodium(sortedUsers.slice(0, 3)); // Top 3 for the podium
}

function updateLeaderboardTable(users) {
    const tbody = document.querySelector('.leaderboard-table tbody');
    tbody.innerHTML = ''; // Clear existing rows

    for (let i = 3; i < 10; i++) {
        const user = users[i];
        const rank = i + 1;

        const row = document.createElement('tr');

        if (user) {
            // If there is a user, display their info
            row.innerHTML = `
                <td>#${rank}</td>
                <td>${user.username}</td>
                <td><span class="highlight">$</span> ${user.wager.toFixed(2)}</td>
                <td>${rank > 3 ? `<span class="highlight">$</span> ${rank === 4 ? 75 : (rank === 5 ? 25 : 0)}` : ''}</td>
            `;
        } else {
            // If no user, display N/A and 0.00
            row.innerHTML = `
                <td>#${rank}</td>
                <td>N/A</td>
                <td><span class="highlight">$</span> 0.00</td>
                <td>${rank > 3 ? `<span class="highlight">$</span> ${rank === 4 ? 75 : (rank === 5 ? 25 : 0)}` : ''}</td>
            `;
        }

        tbody.appendChild(row);
    }
}

function updatePodium(topUsers) {
    topUsers.forEach((user, index) => {
        const podiumBox = document.getElementById(`${index + 1}`);
        if (user == null) {
            podiumBox.querySelector('.name').textContent = "N/A";
            podiumBox.querySelector('.wagered-amount h4').innerHTML = `<span class="highlight">$ </span>0.00}`;
        } else {
            if (podiumBox) {
                podiumBox.querySelector('.name').textContent = user.username;
                podiumBox.querySelector('.wagered-amount').innerHTML = `<span class="highlight">$ </span>${user.wager.toFixed(2)}`;
            }
        }
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    await updateLeaderboard();
});
