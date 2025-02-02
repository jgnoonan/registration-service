# Connect to Azure AD
Connect-AzureAD

# Import CSV file with format: UserPrincipalName,PhoneNumber
# Example CSV:
# user@company.com,+14235551234
$users = Import-Csv "users.csv"

foreach ($user in $users) {
    try {
        # Get the user
        $aadUser = Get-AzureADUser -Filter "userPrincipalName eq '$($user.UserPrincipalName)'"
        
        # Update mobile phone
        Set-AzureADUser -ObjectId $aadUser.ObjectId -Mobile $user.PhoneNumber
        
        Write-Host "Updated phone for $($user.UserPrincipalName)"
    }
    catch {
        Write-Host "Error updating $($user.UserPrincipalName): $_"
    }
}
