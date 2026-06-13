# 🚀 GitHub Pages Deployment Guide

This document explains how to deploy the Java Learning Hub to GitHub Pages.

## 📋 Prerequisites

- GitHub account
- This repository pushed to GitHub
- Git installed locally

## 🎯 Step-by-Step Deployment

### Step 1: Enable GitHub Pages

1. Go to your GitHub repository
2. Navigate to **Settings** (gear icon in the top right)
3. Scroll down to **"Pages"** section on the left sidebar
4. Under "Build and deployment":
   - Select **"Deploy from a branch"**
   - Choose branch: **main**
   - Choose folder: **/docs**
5. Click **"Save"**

### Step 2: Wait for Deployment

GitHub will automatically deploy your site. This may take a few minutes.

You'll see a notification in the Pages section with your site URL:
```
Your site is live at: https://yourusername.github.io/OOPS/
```

### Step 3: Access Your Site

Visit the URL provided to see your Java Learning Hub live!

## 🔄 Automatic Deployment with GitHub Actions

The repository includes a GitHub Actions workflow (`.github/workflows/deploy.yml`) that automatically deploys changes to GitHub Pages whenever you push to the `main` branch.

### Configure Automatic Deployment

1. The workflow file is already in `.github/workflows/deploy.yml`
2. Make sure your repository has the GitHub Token (default `GITHUB_TOKEN`)
3. Any push to `main` branch will automatically trigger deployment

## 📝 Making Changes

### Update Content

1. Edit HTML files in the `docs/` folder
2. Commit your changes:
   ```bash
   git add .
   git commit -m "Update: Add new content to Java Basics"
   git push origin main
   ```
3. GitHub Actions will automatically deploy within 1-2 minutes
4. Visit your site to see the changes

### Add New Module

1. Create a new folder in `docs/`:
   ```bash
   mkdir docs/my-module
   ```

2. Create `index.html` file following the template from existing modules

3. Update the navigation in `docs/index.html` to link to your new module

4. Commit and push:
   ```bash
   git add .
   git commit -m "Add new learning module"
   git push origin main
   ```

## 🎨 Customization

### Update Site Title & Description

Edit `docs/index.html`:
```html
<title>Your Custom Title - Java Learning Hub</title>
```

### Change Color Scheme

Edit `docs/styles.css` - look for `:root` variables:
```css
:root {
    --primary-color: #FF7F50;    /* Main orange color */
    --secondary-color: #4A90E2;  /* Blue accent */
    --dark-bg: #1a1a1a;          /* Dark background */
    --light-bg: #f5f5f5;         /* Light background */
}
```

### Add Custom Domain

1. In **Settings → Pages**, under "Custom domain":
2. Enter your domain (e.g., `javalearning.dev`)
3. Create a `CNAME` file in `docs/` with your domain:
   ```
   javalearning.dev
   ```
4. Commit and push

## 🐛 Troubleshooting

### Site Not Updating

1. Check that `/docs` folder is selected in GitHub Pages settings
2. Clear your browser cache (Ctrl+Shift+Delete or Cmd+Shift+Delete)
3. Wait a few minutes for deployment to complete
4. Check GitHub Actions tab to see if deployment succeeded

### GitHub Pages Not Enabled

1. Go to Settings → Pages
2. Make sure "Deploy from a branch" is selected
3. Verify `/docs` folder is selected
4. Click Save

### Custom Domain Not Working

1. Verify DNS settings are correct
2. Create a `CNAME` file in the `docs/` folder
3. Wait 24 hours for DNS propagation

## 📊 Monitor Deployments

1. Go to **Actions** tab in your GitHub repository
2. Click **"Deploy to GitHub Pages"** workflow
3. View deployment logs and status

## 🔗 Useful Links

- GitHub Pages Documentation: https://docs.github.com/en/pages
- GitHub Actions Docs: https://docs.github.com/en/actions
- Deploy HTML to GitHub Pages: https://github.blog/2016-08-17-simpler-github-pages-publishing/

## 💡 Tips

1. **Keep docs/ folder clean** - Only static HTML/CSS/JS files
2. **Use consistent naming** - Use lowercase and hyphens for folder names
3. **Test locally first** - Open HTML files in browser before pushing
4. **Regular updates** - Keep content fresh and up-to-date
5. **Monitor traffic** - GitHub Pages settings show visitor stats

## 🎓 Next Steps

1. ✅ Deploy to GitHub Pages
2. ✅ Share the link with your community
3. ✅ Gather feedback
4. ✅ Improve content based on feedback
5. ✅ Promote on social media

---

**Your Java Learning Hub is now live! Share it with juniors and the community! 🎉**

