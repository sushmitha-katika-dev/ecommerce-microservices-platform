$services = @("api-gateway", "user-service", "product-service", "cart-service", "order-service", "payment-service", "notification-service")

foreach ($service in $services) {
    Write-Host "Fixing imports for $service..."
    $serviceSnake = $service.Replace('-', '_')
    $basePackage = "com.ecommerce.$serviceSnake"
    $srcMain = "C:\spring-workspace\ecommerce-microservices-platform\$service\src\main\java"
    $baseDir = "$srcMain\com\ecommerce\$serviceSnake"
    
    if (-not (Test-Path $srcMain)) { continue }

    # Find all subdirectories that contain .java files
    $validPackages = @()
    if (Test-Path $baseDir) {
        $subDirs = Get-ChildItem -Path $baseDir -Recurse -Directory
        foreach ($dir in $subDirs) {
            $javaFiles = Get-ChildItem -Path $dir.FullName -File -Filter *.java
            if ($javaFiles.Count -gt 0) {
                $relPath = $dir.FullName.Substring($baseDir.Length + 1).Replace('\', '.')
                $validPackages += "import ${basePackage}.${relPath}.*;"
            }
        }
    }
    
    $dynamicWildcards = $validPackages -join "`n"

    $allFiles = Get-ChildItem -Path $srcMain -Recurse -File -Filter *.java
    if ($allFiles) {
        foreach ($file in $allFiles) {
            $content = Get-Content $file.FullName -Raw
            
            # Remove the old hardcoded wildcards
            $content = $content -replace "(?m)^import\s+com\.ecommerce\.[^;]+\.\*;\r?\n?", ""
            
            # Inject dynamic wildcards
            if ($dynamicWildcards) {
                $content = $content -replace "(?m)^(package\s+[^;]+;)", "`$1`n`n$dynamicWildcards"
            }
            
            # Strip BOM and write
            $bytes = [System.Text.Encoding]::UTF8.GetBytes($content)
            [System.IO.File]::WriteAllBytes($file.FullName, $bytes)
        }
    }
}
Write-Host "Fix imports complete."
