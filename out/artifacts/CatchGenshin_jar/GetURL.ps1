
$logLocation = "%userprofile%\AppData\LocalLow\miHoYo\Genshin Impact\output_log.txt";
$logLocationChina = "%userprofile%\AppData\LocalLow\miHoYo\$([char]0x539f)$([char]0x795e)\output_log.txt";

$reg = $args[0]

#Write-Host "$([char]0x6b63)$([char]0x5728)$([char]0x83b7)$([char]0x53d6)$([char]0x62bd)$([char]0x5361)$([char]0x5206)$([char]0x6790)$([char]0x94fe)$([char]0x63a5)..." -ForegroundColor Green
Write-Host " "
$logLocation = $logLocationChina

$tmps = $env:TEMP + '\pm.ps1';
if ([System.IO.File]::Exists($tmps)) {
  ri $tmps
}

$path = [System.Environment]::ExpandEnvironmentVariables($logLocation);
if (-Not [System.IO.File]::Exists($path)) {
    Write-Host "$([char]0x627e)$([char]0x4e0d)$([char]0x5230)$([char]0x539f)$([char]0x795e)$([char]0x65e5)$([char]0x5fd7)$([char]0x6587)$([char]0x4ef6)$([char]0x8bf7)$([char]0x81f3)$([char]0x5c11)$([char]0x6253)$([char]0x5f00)$([char]0x7948)$([char]0x613f)$([char]0x5386)$([char]0x53f2)$([char]0x754c)$([char]0x9762)$([char]0x4e00)$([char]0x6b21)" -ForegroundColor Red
  
    if (-NOT ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")) {  
        Write-Host "$([char]0x662f)$([char]0x5426)$([char]0x4ee5)$([char]0x7ba1)$([char]0x7406)$([char]0x5458)$([char]0x8eab)$([char]0x4efd)$([char]0x6765)$([char]0x8fd0)$([char]0x884c)$([char]0x811a)$([char]0x672c)$([char]0x003f)$([char]0x6309)$([char]0x0045)$([char]0x006e)$([char]0x0074)$([char]0x0065)$([char]0x0072)$([char]0x952e)$([char]0x6765)$([char]0x7ee7)$([char]0x7eed)$([char]0x6216)$([char]0x6309)$([char]0x5176)$([char]0x4ed6)$([char]0x952e)$([char]0x6765)$([char]0x53d6)$([char]0x6d88)"
        $keyInput = [Console]::ReadKey($true).Key
        if ($keyInput -ne "13") {
            return
        }

        $myinvocation.mycommand.definition > $tmps

        Start-Process powershell -Verb runAs -ArgumentList "-noexit", $tmps, $reg
        break
    }

    return
}

$logs = Get-Content -Path $path -Encoding UTF8
$m = $logs -match "(?m).:/.+(GenshinImpact_Data|YuanShen_Data)"
$m[0] -match "(.:/.+(GenshinImpact_Data|YuanShen_Data))" >$null

if ($matches.Length -eq 0) {
    Write-Host "$([char]0x627e)$([char]0x4e0d)$([char]0x5230)$([char]0x539f)$([char]0x795e)$([char]0x65e5)$([char]0x5fd7)$([char]0x6587)$([char]0x4ef6)$([char]0x8bf7)$([char]0x81f3)$([char]0x5c11)$([char]0x6253)$([char]0x5f00)$([char]0x7948)$([char]0x613f)$([char]0x5386)$([char]0x53f2)$([char]0x754c)$([char]0x9762)$([char]0x4e00)$([char]0x6b21)" -ForegroundColor Red
    return
}

$gamedir = $matches[1]
$cachefile = "$gamedir/webCaches/Cache/Cache_Data/data_2"
$tmpfile = "$env:TEMP/ch_data_2"

Copy-Item $cachefile -Destination $tmpfile

$content = Get-Content -Encoding UTF8 -Raw $tmpfile
$splitted = $content -split "1/0/"
$found = $splitted -match "https.+webstatic.mihoyo.com/.+?game_biz=hk4e_(global|cn)"

$found = $found[$found.Length - 1] -match "(https.+?game_biz=hk4e_(global|cn))"

Remove-Item $tmpfile

if (-Not $found) {
    Write-Host "$([char]0x627e)$([char]0x4e0d)$([char]0x5230)$([char]0x539f)$([char]0x795e)$([char]0x65e5)$([char]0x5fd7)$([char]0x6587)$([char]0x4ef6)$([char]0x8bf7)$([char]0x81f3)$([char]0x5c11)$([char]0x6253)$([char]0x5f00)$([char]0x7948)$([char]0x613f)$([char]0x5386)$([char]0x53f2)$([char]0x754c)$([char]0x9762)$([char]0x4e00)$([char]0x6b21)" -ForegroundColor Red
    return
}

$wishHistoryUrl = $matches[0] + "#/log"
$PSDefaultParameterValues['Out-File:Encoding'] = 'utf8'
Write-Host $wishHistoryUrl
Set-Clipboard -Value $wishHistoryUrl
$wishHistoryUrl.ToString() | Out-File "./url.txt"
Write-Host " "
#Write-Host "$([char]0x62bd)$([char]0x5361)$([char]0x5206)$([char]0x6790)$([char]0x5730)$([char]0x5740)$([char]0x83b7)$([char]0x53d6)$([char]0x6210)$([char]0x529f)$([char]0xff0c)$([char]0x8bf7)$([char]0x590d)$([char]0x5236)$([char]0x5230)$([char]0x201c)$([char]0x63d0)$([char]0x74e6)$([char]0x7279)$([char]0x5c0f)$([char]0x52a9)$([char]0x624b)$([char]0x201d)$([char]0x4f7f)$([char]0x7528)" -ForegroundColor Green
#Write-Host "$([char]0x63d0)$([char]0x74e6)$([char]0x7279)$([char]0x5c0f)$([char]0x52a9)$([char]0x624b)$([char]0x795d)$([char]0x60a8)$([char]0x6c14)$([char]0x6ee1)$([char]0x6ee1)$([char]0xff0c)$([char]0x5341)$([char]0x8fde)$([char]0x51fa)$([char]0x91d1)$([char]0xff0c)$([char]0x5c0f)$([char]0x4fdd)$([char]0x5e95)$([char]0x5fc5)$([char]0x4e2d)$([char]0xff01)" -ForegroundColor Yellow