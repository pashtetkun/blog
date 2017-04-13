# Blog created client-side React and server-side Java.

  1. Require nodejs with npm
  2. Clone this branch (git clone https://github.com/kotAndEgor/blog.git -b onSparkServer --single-branch) - in terminal
  3. Go to folder: "blog"
  4. Open terminal and enter command: "npm install", "npm run compile-js", possibly "npm install react"
  5. Import folder "server" with IntellijIDEA as Maven project
  6. Go to server/src/main/java/ and run Main.java
  7. Open "localhost:8080" in browser

### How add image in gallery
  1. Copy image in blog/public/images/gallery/
  2. Open blog/app/constants.js and insert in array IMAGES new object where element = {src: '/images/gallery/'name-image.jpg'}

### How create category, subcategory, article
- Category
    - Add folder in blog/repo/name-category(folder)
    - Go to blog/app/translation.js, add in file 'name-category': 'your name'

- Subcategory
    - Add folder in blog/repo/article/(folder)
    - Go to blog/app/translation.js, add in file 'name-subcategory': 'your name'

- Article
    - Add file.md in blog/repo/article/review/(your MD file)
