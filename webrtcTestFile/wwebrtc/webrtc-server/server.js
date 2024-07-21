const express = require('express');
const multer = require('multer');
const path = require('path');

const app = express();
const port = 3000;

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, 'uploads/');
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + path.extname(file.originalname));
  }
});

const upload = multer({ storage: storage });

app.post('/upload', upload.single('image'), (req, res) => {
  res.send('File uploaded successfully');
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
