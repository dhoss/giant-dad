function calendarInit(user) {
    document.addEventListener('DOMContentLoaded', function () {
        const closeBtn = document.querySelector("#view-workout-modal-close");
        const workoutForm = document.querySelector("#workout-data");

        workoutForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const workoutFormData = new FormData(workoutForm);

            const alertDiv = document.createElement("div");
            const alertSuccessDiv = document.getElementById("workout-added-success");
            const alertFailedDiv = document.getElementById("workout-added-failure");

            const closeSavedSuccessButton = document.getElementById("close-workout-saved-success");
            const closeSavedFailedButton = document.getElementById("close-workout-saved-failed");
            closeSavedSuccessButton.addEventListener("click", (e) => {
                alertSuccessDiv.classList.add("d-none");
                alertSuccessDiv.replaceChildren(closeSavedSuccessButton);
            });
            closeSavedFailedButton.addEventListener("click", (e) => {
                alertFailedDiv.classList.add("d-none");
                alertFailedDiv.replaceChildren(closeSavedFailedButton);
            });

            let liftData = $('#workout-data').serializeJSON({useIntKeysAsArrayIndex: true});
            try {
                // TODO: put this URL in config
                const response = await fetch('http://settra:8080/api/workouts/save', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        // TODO: implement me
                        user: {id: 1},
                        forDay: workoutFormData.get("forDay"),
                        lifts: liftData.lifts,
                        scheduledDate: workoutFormData.get("scheduledDate")
                    })
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const result = await response.json();
                alertSuccessDiv.classList.remove("d-none");
                // TODO: figure out why this is just appending and not removing previous alerts
                alertDiv.innerHTML = "Workout saved";
                document.getElementById("workout-added-success").append(alertDiv);
            } catch (error) {
                alertFailedDiv.classList.remove("d-none");
                alertDiv.innerHTML = `Error saving workout: ${error}`;
                document.getElementById("workout-added-failure").append(alertDiv);
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
                const date = info.dateStr;
                document.getElementById('view-workout-modal-day').innerHTML = `<p>${date}</p>`;
                retrieveWorkout(user, date).then((workout) => buildWorkoutForm(workout, date));
                const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById('view-workout-modal'));
                closeBtn.addEventListener("click", () => modal.hide());
                modal.show();
            }
        }).render();

    });
}

// TODO: clean this up
function buildWorkoutForm(workoutData, date) {
    const workoutDataContainer = document.getElementById("workout-data-container");
    // TODO: figure out why the button placement is wrong
    const saveButtonDiv = document.getElementById("workout-save-button-div");

    saveButtonDiv.remove();
    workoutDataContainer.textContent = "";
    workoutDataContainer.appendChild(saveButtonDiv);

    saveButtonDiv.hidden = false;

    // TODO: figure out why this isn't working
    if (!workoutData || !workoutData.workout || Object.keys(workoutData.workout).length === 0) {
        saveButtonDiv.hidden = true;
        const noWorkoutsDiv = document.createElement("div");
        noWorkoutsDiv.innerHTML = `<div>No workouts for today</div>`;
        workoutDataContainer.appendChild(noWorkoutsDiv);
        return;
    }

    const liftData = workoutData.workout.lifts || [];

    requestAnimationFrame(() => {
        const fragment = document.createDocumentFragment();

        const hiddenLiftDayInputDiv = document.createElement('div');
        hiddenLiftDayInputDiv.innerHTML = `<input type="hidden" name="forDay" value="${workoutData.workout.forDay}">`;
        fragment.appendChild(hiddenLiftDayInputDiv);

        const hiddenScheduledDateInputDiv = document.createElement('div');
        hiddenScheduledDateInputDiv.innerHTML = `<input type="hidden" name="scheduledDate" value="${date}">`;
        fragment.appendChild(hiddenScheduledDateInputDiv);

        for (const [index, lift] of liftData.entries()) {
            const liftInputDiv = document.createElement('div');
            liftInputDiv.className = "mb-3";

            liftInputDiv.innerHTML = `
                <div class="col-12">
                    <label for="lift-name-${index}" class="form-label">Lift</label>
                    <input name="lifts[${index}][name]" class="form-control" id="lift-name-${index}" value="${lift.name}">
                </div>`;

            const liftSets = lift.sets || {};
            const toSorted = Object.entries(liftSets).toSorted((a, b) => a[0].localeCompare(b[0]));

            for (const [setIndex, set] of toSorted) {
                const setRowDiv = document.createElement('div');
                setRowDiv.className = "row mt-2";
                setRowDiv.innerHTML = `
                    <input id="lift-${index}-set-${setIndex}-order" name="lifts[${index}][sets][${setIndex}][setNumber]" type="hidden" value="${set.setNumber}">
                    <div class="col">
                        <label for="lift-${index}-set-${setIndex}-weight" class="form-label">Set Weight</label>
                        <input id="lift-${index}-set-${setIndex}-weight" name="lifts[${index}][sets][${setIndex}][weight]" type="text" value="${set.weight}" class="form-control" placeholder="0" aria-label="Set Weight">
                    </div>
                    <div class="col">
                        <label for="lift-${index}-set-${setIndex}-reps" class="form-label">Set Reps</label>
                        <input id="lift-${index}-set-${setIndex}-reps" name="lifts[${index}][sets][${setIndex}][reps]" type="text" value="${set.reps}" class="form-control" placeholder="0" aria-label="Set Reps">
                    </div>`;
                liftInputDiv.appendChild(setRowDiv);
            }

            fragment.appendChild(liftInputDiv);
        }

        workoutDataContainer.appendChild(fragment);
    });
}

// TODO: take base URL as parameter
async function retrieveWorkout(user, date) {
    try {
        const res = await fetch(`http://settra:8080/api/workouts/${user}/${date}`);
        if (!res.ok) throw new Error(`Error retrieving workout data for ${date}: ${res.status} -> ${await res.text()}`);
        const result = await res.json();
        if (result === undefined)
            return {};
        return result;
    } catch (error) {
        console.error(error);
        // TODO: update response div with error
    }
}