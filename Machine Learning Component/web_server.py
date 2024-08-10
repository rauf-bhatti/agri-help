import os
from flask import Flask, request, jsonify
import io
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import datasets, layers, models
from keras.utils import to_categorical
import matplotlib.pyplot as plt
import numpy as np
import cv2
import os
import random
import pickle
import PIL
import PIL.Image

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = 'uploads'

@app.route('/upload', methods=['POST'])
def upload_file():
    file = request.files['image']
    if file:
        file.save('file_to_read.jpg')
        return load_image('file_to_read.jpg')
        # return 'File uploaded successfully', 200
    else:
        return 'No file uploaded', 400
    
def preprocess_image(image, target_size):
    image = image.resize(target_size)
    image = tf.keras.utils.array_to_img(image)
    image = np.expand_dims(image, axis = 0)
    return image

def load_image(path):
    reconstructed_model = keras.models.load_model("cnn_fyp2.h5")
    class_names = ["bacterial_blight", "curl_virus", "fussarium_wilt", "healthy"]
    img = PIL.Image.open(path)
    p_img = preprocess_image(img, target_size=(256, 256))
    p_img = p_img.reshape(-1, 256, 256, 3)
    prediction = reconstructed_model.predict(p_img).tolist()

    response = {
        "prediction": {
            "bacterial_blight": prediction[0][0],
            "curl_virus": prediction[0][1],
            "fussarium_wilt": prediction[0][2],
            "healthy": prediction[0][3],
            "predicted_label": class_names[np.argmax(prediction)]
        }
    }

    print("response:")
    print(response)
    return jsonify(response)