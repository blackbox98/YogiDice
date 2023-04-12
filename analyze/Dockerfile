FROM python:3.7.9
WORKDIR /var/jenkins_home/workspace/yogidicedeploy/analyze
COPY requirements.txt ./

RUN pip install --upgrade pip
RUN pip install -r requirements.txt
COPY . .
CMD ["gunicorn", "analyze.wsgi", "--bind", "0.0.0.0:8000"]