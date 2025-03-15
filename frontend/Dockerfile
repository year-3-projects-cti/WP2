# Imagine oficială cu Node.js
FROM node:18

# Setăm directorul de lucru
WORKDIR /app

# Copiem fișierele și instalăm dependințele
COPY package.json package-lock.json ./
RUN npm install

# Copiem codul sursă
COPY . .

# Expunem portul Vue.js
EXPOSE 5173

# Comanda de start
CMD ["npm", "run", "dev"]
