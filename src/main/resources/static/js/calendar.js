document.addEventListener('DOMContentLoaded', function () {
    const modal = document.querySelector("#view-workout-modal");
    const closeBtn = document.querySelector("#view-workout-modal-close");
    const workoutForm = document.querySelector("#workout-data");

    closeBtn.addEventListener("click", () => modal.close());

    workoutForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const setDataPlaceholderMap = new Map();

        const workoutFormData = new FormData(workoutForm);
        const lift =  workoutFormData.get('lift');

        for (const [key, value] of workoutFormData) {
            const setInfo = key.split(/(\d+)\./);
            let setNum = Number(setInfo[1]);
            let weightOrRepsKey = setInfo[2];
            if (setInfo[0] != null && setNum != null && weightOrRepsKey != null) {
                let newSetObject = {
                    [setNum]: {[weightOrRepsKey]: Number(value)}
                };
                const currentSetData = setDataPlaceholderMap.getOrInsert("set", []);
                for (const s of currentSetData) {
                    newSetObject = _.merge(s, newSetObject);
                }
                setDataPlaceholderMap.set("set", [newSetObject]);
            }
        }

        try {
            // TODO: put this URL in config
            const response = await fetch('http://localhost:8080/api/workouts/save', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    lifts: [
                        {
                            name: lift,
                            sets: (setDataPlaceholderMap.get("set"))[0]
                        }
                    ]
                })
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            console.log('Success:', result);
        } catch (error) {
            console.error("Error saving workout:", error);
        }
    });

    new FullCalendar.Calendar(document.getElementById('calendar'), {
        timeZone: 'UTC',
        themeSystem: 'bootstrap5',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
        },
        weekNumbers: true,
        dayMaxEvents: true, // allow "more" link when too many events
        events: '/api/programs',
        dateClick: function (info) {
            document.getElementById('view-workout-modal-day').innerHTML = info.dateStr;
            modal.showModal();
        }
    }).render();

});