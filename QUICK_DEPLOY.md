# QUICK DEPLOY TO GITHUB PAGES

## 5-Minute Deployment Guide (For Windows PowerShell)

### Step 1: Initialize Git Repository (If Not Already Done)

```powershell
cd C:\OOPS
git init
git add .
git commit -m "Java Learning Hub - Complete Educational Platform"
```

### Step 2: Add Remote Repository

Replace `yourusername` with your GitHub username:

```powershell
git remote add origin https://github.com/yourusername/java-learning-hub.git
git branch -M main
git push -u origin main
```

### Step 3: Enable GitHub Pages

1. Go to: https://github.com/yourusername/java-learning-hub/settings
2. Click **"Pages"** in the left sidebar
3. Under **"Build and deployment"**:
   - Source: **Deploy from a branch**
   - Branch: **main**
   - Folder: **/docs**
4. Click **Save**

### Step 4: Wait & Visit

GitHub will deploy automatically (1-2 minutes). Your site will be live at:

```
https://yourusername.github.io/java-learning-hub/
```

## Verification Checklist

- [ ] Repository created on GitHub
- [ ] Repository has `main` branch
- [ ] `/docs` folder exists
- [ ] GitHub Pages is enabled
- [ ] Status shows "Your site is live"
- [ ] Can access site in browser

## Update Content Later

When you make changes:

```powershell
# Make your changes in docs/ folder
# Then:

git add .
git commit -m "Update: Add new content"
git push origin main

# GitHub Actions will auto-deploy!
```

## Full Commands List

```powershell
# 1. Navigate to project
cd C:\OOPS

# 2. Check status
git status

# 3. Stage all changes
git add .

# 4. Commit changes
git commit -m "Java Learning Hub - Initial Release"

# 5. Push to GitHub
git push origin main

# 6. Check deployment status
# Go to: Settings > Pages (in your GitHub repo)
```

## Your URLs

After deployment, share these links:

| Page | URL |
|------|-----|
| Homepage | `https://yourusername.github.io/java-learning-hub/` |
| Java Basics | `https://yourusername.github.io/java-learning-hub/java-basics/` |
| OOP | `https://yourusername.github.io/java-learning-hub/oops/` |
| DSA | `https://yourusername.github.io/java-learning-hub/dsa/` |
| Backend | `https://yourusername.github.io/java-learning-hub/java-backend/` |
| Spring AI | `https://yourusername.github.io/java-learning-hub/spring-ai/` |
| Resources | `https://yourusername.github.io/java-learning-hub/resources.html` |

## Troubleshooting

### Problem: "git: command not found"
**Solution:** Install Git from https://git-scm.com/

### Problem: "Permission denied" when pushing
**Solution:** Set up SSH key or use GitHub CLI token

### Problem: Site not showing "live"
**Solution:** 
- Wait 2-3 minutes
- Hard refresh (Ctrl+Shift+R)
- Check Status in Pages settings

### Problem: Changes not showing
**Solution:**
- Clear browser cache (Ctrl+Shift+Delete)
- Check if push was successful (`git status`)
- Wait a minute and refresh

## Pro Tips

1. **Test Locally First**
   ```powershell
   # Open in browser (Windows)
   start docs/index.html
   ```

2. **View Git History**
   ```powershell
   git log --oneline
   ```

3. **Undo Last Commit**
   ```powershell
   git reset --soft HEAD~1
   ```

4. **Create Custom Domain**
   - Buy domain name
   - Add DNS records
   - Update in GitHub Pages settings

## Need Help?

- **GitHub Docs:** https://docs.github.com/en/pages
- **Git Tutorial:** https://git-scm.com/doc
- **PowerShell:** Built-in on Windows

---

**You're deployed! Now share your hub with the world!**






