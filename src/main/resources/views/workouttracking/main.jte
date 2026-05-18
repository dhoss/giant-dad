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
    <script src="/js/calendar.js"></script>
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
                        <label for="lift0" class="form-label">Lift</label>
                        <input name="lift" class="form-control" id="lift0">
                    </div>
                    <div class="row">
                        <div class="col">
                            <label for="set0weight" class="form-label">Set Weight</label>
                            <input id="set0weight" name="set0.weight" type="text" class="form-control" placeholder="0" aria-label="Set Weight">
                        </div>
                        <div class="col">
                            <label for="set0reps" class="form-label">Set Reps</label>
                            <input id="set0reps" name="set0.reps" type="text" class="form-control" placeholder="0" aria-label="Set Reps">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <label for="set1weight" class="form-label">Set Weight</label>
                            <input id="set1weight" name="set1.weight" type="text" class="form-control" placeholder="0" aria-label="Set Weight">
                        </div>
                        <div class="col">
                            <label for="set1reps" class="form-label">Set Reps</label>
                            <input id="set1reps" name="set1.reps" type="text" class="form-control" placeholder="0" aria-label="Set Reps">
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