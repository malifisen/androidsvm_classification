from flask import Flask,request,jsonify
import numpy as np
import pickle
model = pickle.load(open('model.pkl','rb'))
app = Flask(__name__)
@app.route('/')
def index():
    return "Hello world"
@app.route('/predict',methods=['POST'])
def predict():
    area = request.form.get('area')
    perimeter = request.form.get('perimeter')
    diameter = request.form.get('diameter')
    mean = request.form.get('mean')
    deviation = request.form.get('deviation')
    smoothness = request.form.get('smoothness')
    skewness = request.form.get('skewness')
    uniformity = request.form.get('uniformity')
    entropy = request.form.get('entropy')
    input_query = np.array([[area,perimeter,diameter,mean,deviation,smoothness,skewness,uniformity,entropy]])
    result = model.predict(input_query)[0]
    return jsonify({'class':str(result)})
if __name__ == '__main__':
    app.run(debug=True)