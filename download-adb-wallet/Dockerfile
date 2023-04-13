FROM python:3.9-slim
WORKDIR /oci
RUN pip install oci
COPY *.py ./
ENTRYPOINT [ "python", "/oci/download-adb-wallet.py" ]
