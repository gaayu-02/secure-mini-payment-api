let token = "";

function showSection(sectionId) {
    document.querySelectorAll(".section").forEach(sec => {
        sec.classList.add("hidden");
    });
    document.getElementById(sectionId).classList.remove("hidden");
}

async function register() {
    const res = await fetch("http://localhost:8080/api/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            email: regEmail.value,
            password: regPassword.value
        })
    });

    alert(await res.text());
}

async function login() {
    const res = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            email: loginEmail.value,
            password: loginPassword.value
        })
    });

    const data = await res.json();
    token = data.token;
    alert("Login Successful!");
}

async function makePayment() {
    const res = await fetch("http://localhost:8080/api/payment", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({
            amount: amount.value,
            currency: currency.value,
            merchantId: merchantId.value,
            referenceId: referenceId.value
        })
    });

    alert(await res.text());
}

async function getTransactions() {
    const res = await fetch("http://localhost:8080/api/transactions", {
        headers: { "Authorization": "Bearer " + token }
    });

    const data = await res.json();
    transactionList.innerHTML = "";

    data.forEach(tx => {
        const li = document.createElement("li");
        li.innerText = `${tx.amount} ${tx.currency} | Ref: ${tx.referenceId}`;
        transactionList.appendChild(li);
    });
}