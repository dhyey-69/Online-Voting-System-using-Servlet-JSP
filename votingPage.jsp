<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Voting Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('./0__9D1GLR9jb08x4g5.jpg');
            background-size: 99% 99%;
            background-position: center;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            position: relative;
            overflow: hidden;
        }

        .glass-panel {
            background-color: rgba(255, 255, 255, 0.7); /* Adjust the alpha value for transparency */
            backdrop-filter: blur(10px);
            border-radius: 5px;
            box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.1);
            padding: 20px;
            text-align: center;
        }

        h2 {
            margin-bottom: 20px;
        }

        input[type="radio"] {
            margin-bottom: 10px;
        }

        button {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="glass-panel">
        <h2>Parties</h2>
        <form action="vote" method="post">
            <input type="radio" name="candidate_id" value="1">Bharatiya Janata Party<br><br>
            <input type="radio" name="candidate_id" value="2">Indian National Congress<br><br>
            <input type="radio" name="candidate_id" value="2">Aam Aadmi Party<br><br>
            <input type="radio" name="candidate_id" value="2">Rashtriya Janata Dal<br><br>
            <input type="radio" name="candidate_id" value="2">Trinamool Congress<br><br>
            <button type="submit">Vote</button>
        </form>
    </div>
</body>
</html>
