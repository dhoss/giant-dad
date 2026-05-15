<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>Giant Dad Workout Tracker - Track Workouts</title>
    <style>

        html, body {
            margin: 0;
            padding: 0;
            font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
            font-size: 14px;
        }

        #calendar {
            max-width: 1100px;
            margin: 40px auto;
        }

    </style>

    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.20/index.global.min.js'></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css' rel='stylesheet'>
    <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/bootstrap5@6.1.20/index.global.min.js'></script>
    <script src="https://cdn.jsdelivr.net/npm/lodash@4.18.1/lodash.min.js"></script>
    <script>
        // TODO: move to javascript file
@raw
        document.addEventListener('DOMContentLoaded', function () {
            const modal = document.querySelector("#view-workout-modal");
            const closeBtn = document.querySelector("#view-workout-modal-close");
            const workoutForm = document.querySelector("#workout-data");

            closeBtn.addEventListener("click", () => modal.close());

            workoutForm.addEventListener("submit", async (e) => {
                e.preventDefault();
// TODO: code for parsing nested fields
                const setData = new Map();

                const workoutFormData = new FormData(workoutForm);
                const workoutFormJson = Object.fromEntries(workoutFormData.entries());
                const lift =  workoutFormData.get('lift');

                for (const [key, value] of workoutFormData) {
                    console.log("***** WORKOUTFORMJSON " + key + " -> " + value);
                    const setInfo = key.split(/(\d+)\./);
                    console.log("***** SETINFO " + setInfo);
                    if (setInfo[0] != null && setInfo[1] != null && setInfo[2] != null) {
                        let newSetObject = {
                            [setInfo[1]]: {[setInfo[2]]: value}
                        };
                        console.log("**** NEW SET OBJECT " + JSON.stringify(newSetObject));
                        if (setData.has("set")) {
                            const currentSetData = setData.get("set");
                            console.log("**** CURRENT SET DATA " + JSON.stringify(currentSetData));
                            for (const s of currentSetData) {
                                console.log("**** CURRENT SET DATA ELEMENT " + JSON.stringify(s));
                                newSetObject = _.merge(s, newSetObject);//Object.assign({}, newSetObject, s);

                                console.log("**** UPDATED NEW SET OBJECT " + JSON.stringify(newSetObject));
                            }
                            setData.set("set", [newSetObject]);
                        } else {
                            setData.getOrInsert("set", []).push(newSetObject);
                        }
                    }
                    console.log("**** SET PARTS " + setInfo[0] + " -> " + setInfo[1] + " -> " + setInfo[2] + " -> " +value);
                    console.log("**** CURRENT SET DATA " +JSON.stringify(Object.fromEntries(setData.entries())));


                }
                try {
                    const response = await fetch('http://localhost:8080/api/programs/save', {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                          lifts: [
                              {
                                  name: lift//,
//                                  sets: workoutFormJson
                              }
                          ]
                        })//workoutFormForJson)

                    });

                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }

                    const result = await response.json(); // Parse the server's response
                    console.log('Success:', result);
                } catch (error) {
                    console.error("Error saving workout:", error);
                }
            });

            var calendarEl = document.getElementById('calendar');

            var calendar = new FullCalendar.Calendar(calendarEl, {
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
                dateClick: function(info) {
                    document.getElementById('view-workout-modal-day').innerHTML = info.dateStr;
                    modal.showModal();
                }
            });

            calendar.render();

        });
@endraw
    </script>
</head>
<body>
<main>
    <div class="d-flex justify-content-center">
    <div id="calendar"></div>
    <dialog id="view-workout-modal">
        <h2>Workout</h2>
        <div id="view-workout-modal-day"></div>
        <form id="workout-data" class="row g-3">
            <div class="row">
                <div class="col-12">
                    <label for="lift-data-list" class="form-label">Select lift</label>
                    <input name="lift" class="form-control" list="lift-data-list-options" id="lift-data-list" placeholder="Type to search">
                    <datalist id="lift-data-list-options">
                        <option value="squat">Squat</option>
                        <option value="deadlift">Deadlift</option>
                        <option value="bench">Bench</option>
                    </datalist>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="set-weight" class="form-label">Set Weight</label>
                        <input id="set-weight" name="set0.weight" type="text" class="form-control" placeholder="0" aria-label="Set Weight">
                    </div>
                    <div class="col">
                        <label for="set-reps" class="form-label">Set Reps</label>
                        <input id="set-reps" name="set0.reps" type="text" class="form-control" placeholder="0" aria-label="Set Reps">
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="set-weight" class="form-label">Set Weight</label>
                        <input id="set-weight" name="set1.weight" type="text" class="form-control" placeholder="0" aria-label="Set Weight">
                    </div>
                    <div class="col">
                        <label for="set-reps" class="form-label">Set Reps</label>
                        <input id="set-reps" name="set1.reps" type="text" class="form-control" placeholder="0" aria-label="Set Reps">
                    </div>
                </div>
                <div class="col-12">
                    <button type="submit" id="save-workout" class="btn btn-primary">Save</button>
                </div>
            </div>
        </form>
        <button id="view-workout-modal-close">Close</button>
    </dialog>
    </div>
</main>
</body>
</html>