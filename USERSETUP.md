# Azure AD User Setup Guide for Signal Registration Service

This guide explains how to set up users in Azure Active Directory (Azure AD) for use with the Signal Registration Service.

## Prerequisites

1. Azure AD administrator access
2. Access to the Azure Portal (portal.azure.com)

## Step 1: Create the Application Registration

1. Navigate to Azure Active Directory → App registrations → New registration
2. Fill in the following details:
   ```
   Name: Signal Registration Service
   Supported account types: Accounts in this organizational directory only
   Redirect URI: (Leave blank)
   ```
3. Click "Register"
4. Note down the following values from the Overview page:
   - Application (client) ID
   - Directory (tenant) ID
5. Create a client secret:
   - Go to "Certificates & secrets"
   - Click "New client secret"
   - Add a description (e.g., "Signal Registration")
   - Choose an expiration (recommend 12 months)
   - Copy the secret value immediately (it won't be shown again)

## Step 2: Configure API Permissions

1. In the app registration, go to "API permissions"
2. Click "Add a permission"
3. Select "Microsoft Graph"
4. Select "Delegated permissions"
5. Add the following permissions:
   - User.Read (allows reading user profile)
   - User.ReadBasic.All (allows reading basic profiles of all users)
6. Click "Add permissions"
7. Click "Grant admin consent" for your directory

## Step 3: Enable Resource Owner Password Credentials Flow

1. In the app registration, go to "Authentication"
2. Under "Advanced settings", find "Allow public client flows"
3. Enable "Allow public client flows"
4. Check "Enable the following mobile and desktop flows:"
5. Enable "Resource owner password credentials"
6. Click "Save"

## Step 4: Create Users

### Option A: Using Azure Portal (Recommended for individual users)

1. Navigate to Azure Active Directory → Users → New user
2. Fill in the required fields:
   ```
   Username: user@yourdomain.onmicrosoft.com
   Name: User's full name
   First name: (Optional)
   Last name: (Optional)
   Password: Auto-generate or create your own
   ```

3. Important settings:
   ```
   Account enabled: Yes (checked)
   Require password change: No (unchecked) - IMPORTANT!
   Usage location: Select appropriate country
   ```

4. Click "Create"

5. After creation:
   - Click on the new user
   - Go to "Profile"
   - Add mobile phone in E.164 format (e.g., +14235551234)
   - Click "Save"

### Option B: Using PowerShell (Recommended for bulk users)

```powershell
# Connect to Azure AD
Connect-AzureAD

# Create a user
$PasswordProfile = New-Object -TypeName Microsoft.Open.AzureAD.Model.PasswordProfile
$PasswordProfile.Password = "YourSecurePassword123!"
$PasswordProfile.ForceChangePasswordNextLogin = $false

New-AzureADUser `
    -DisplayName "User Name" `
    -UserPrincipalName "user@yourdomain.onmicrosoft.com" `
    -PasswordProfile $PasswordProfile `
    -AccountEnabled $true `
    -MailNickName "user" `
    -Mobile "+14235551234"
```

## Important Notes

1. **Phone Number Format:**
   - Must be in E.164 format (e.g., +14235551234)
   - Must include country code
   - No spaces or special characters
   - Examples:
     ```
     United States: +14235551234
     United Kingdom: +442071234567
     India: +919876543210
     ```

2. **Password Requirements:**
   - At least 8 characters
   - Contains characters from at least 3 of:
     - Uppercase letters (A-Z)
     - Lowercase letters (a-z)
     - Numbers (0-9)
     - Special characters
   - Not commonly used passwords
   - Not contain user's name or email

3. **User Type:**
   - Must be "Member" (not "Guest")
   - Must be in the same tenant as the application

4. **Common Issues:**
   - Password change required at first login (must be disabled)
   - Phone number in wrong format
   - User account disabled
   - Missing usage location

## Bulk User Import

For adding multiple users, you can use CSV import:

1. Create a CSV file with headers:
   ```csv
   UserPrincipalName,DisplayName,Mobile
   user1@domain.onmicrosoft.com,User One,+14235551234
   user2@domain.onmicrosoft.com,User Two,+14235557890
   ```

2. Use the provided PowerShell script:
   ```powershell
   # Import users from CSV
   Import-Csv "users.csv" | ForEach-Object {
       $PasswordProfile = New-Object -TypeName Microsoft.Open.AzureAD.Model.PasswordProfile
       $PasswordProfile.Password = "TempPass123!"
       $PasswordProfile.ForceChangePasswordNextLogin = $false

       New-AzureADUser `
           -UserPrincipalName $_.UserPrincipalName `
           -DisplayName $_.DisplayName `
           -Mobile $_.Mobile `
           -PasswordProfile $PasswordProfile `
           -AccountEnabled $true `
           -MailNickName ($_.UserPrincipalName.Split('@')[0])
   }
   ```

## Testing User Setup

1. Verify user can sign in to portal.azure.com
2. Verify phone number is set correctly in user profile
3. Test with Signal Registration Service
4. Common errors:
   - "User not found": Check username/UPN
   - "Invalid credentials": Check password, account status
   - "Phone number not found": Check mobile phone format

## Security Recommendations

1. Use strong passwords
2. Enable MFA for administrative accounts
3. Regularly review user access
4. Monitor sign-in activity
5. Set up alerts for suspicious activities

## Support

For issues with:
- User creation: Contact your Azure AD administrator
- Signal Registration Service: Contact Signal support
- Application permissions: Review API permissions in Azure Portal
