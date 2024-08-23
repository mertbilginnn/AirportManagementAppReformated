document.addEventListener('DOMContentLoaded', function() {
    const neredenSelect = document.getElementById('nereden');
    const nereyeSelect = document.getElementById('nereye');
    const gidisTarihiInput = document.getElementById('gidisTarihi');
    const donusTarihiInput = document.getElementById('donusTarihi');
    const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

    function updateNereyeOptions() {
        const selectedNereden = neredenSelect.value;
        const options = Array.from(nereyeSelect.options);

        // Enable all options
        options.forEach(option => option.disabled = false);

        // Disable the selected 'Nereden' option in 'Nereye'
        if (selectedNereden) {
            options.forEach(option => {
                if (option.value === selectedNereden) {
                    option.disabled = true;
                }
            });
        }
    }

    function toggleDonusTarihi() {
        var ciftYonRadio = document.getElementById("ciftYon");
        var donusTarihiDiv = document.getElementById("donusTarihidiv");

        if (ciftYonRadio.checked) {
            donusTarihiDiv.hidden = false;
        } else {
            donusTarihiDiv.hidden = true;
        }
    }

    function updateMinDates() {
        gidisTarihiInput.min = today; // Set minimum departure date to today
        const gidisTarihi = gidisTarihiInput.value;
        donusTarihiInput.min = gidisTarihi; // Set minimum return date to departure date
    }

    neredenSelect.addEventListener('change', updateNereyeOptions);
    document.getElementById("tekYon").checked = true;
    toggleDonusTarihi();

    // Initialize minimum dates on page load
    updateMinDates();

    gidisTarihiInput.addEventListener('change', updateMinDates);

    const form = document.querySelector("form");
    const adultInput = document.getElementById("yetiskinYolcu");
    const studentInput = document.getElementById("ogrenciYolcu");
    const disabledInput = document.getElementById("engelliYolcu");
    const errorMessageDiv = document.getElementById("error-message");

    form.addEventListener("submit", function(event) {
        const adultCount = parseInt(adultInput.value, 10);
        const studentCount = parseInt(studentInput.value, 10);
        const disabledCount = parseInt(disabledInput.value, 10);

        if (adultCount <= 0 && studentCount <= 0 && disabledCount <= 0) {
            event.preventDefault(); // Formun gönderilmesini engelle
            errorMessageDiv.textContent = "En az bir yolcu sayısını belirtmelisiniz.";
            errorMessageDiv.style.display = "block"; // Hata mesajını göster
        } else {
            errorMessageDiv.style.display = "none"; // Hata mesajını gizle
        }
    });

    document.getElementById("tekYon").addEventListener("change", toggleDonusTarihi);
    document.getElementById("ciftYon").addEventListener("change", toggleDonusTarihi);
});

document.getElementById('cancelBtn').addEventListener('click', function () {
    const pnrCode = document.getElementById('pnr').textContent;

    if (confirm('Rezervasyonu iptal etmek istediğinize emin misiniz?')) {
        fetch(`/reservations/delete?pnr_code=${pnrCode}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    alert('Rezervasyon başarıyla iptal edildi.');
                    window.location.href = '/index.html';
                } else {
                    alert('Rezervasyon iptal edilemedi. Lütfen tekrar deneyin.');
                }
            })
            .catch(error => console.error('Error:', error));
    }
});