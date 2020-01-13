function Construct-Credentials([Parameter(Mandatory=$true)][String] $jdbcUrl, 
  [Parameter(Mandatory=$true)][String] $username, 
  [Parameter(Mandatory=$true)][System.Security.SecureString] $password) {
    '{\"jdbcUrl\":\"' + $jdbcUrl + '\", \"username\":\"' + $username + '\", \"password\":\"' + [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($password)) + '\"}'
  }
Write-Host Checking for credhub service -ForegroundColor Magenta

cf marketplace -s credhub
$credhubExists = (0 -eq $LASTEXITCODE)

$jdbcUrl = Read-Host "Enter the JDBC URL: "
$username = Read-Host "Enter the username for the URL: "
$passwordSecure = Read-Host -AsSecureString "Enter the password for the JDBC URL: "

if($credhubExists) {
    cf create-service credhub default multiprofile-db -c "$(Construct-Credentials $jdbcUrl $username $passwordSecure)"
}
else {
    cf cups multiprofile-db -p "$(Construct-Credentials $jdbcUrl $username $passwordSecure)"
}
