$services = @("api-gateway", "user-service", "product-service", "cart-service", "order-service", "payment-service", "notification-service")

foreach ($service in $services) {
    Write-Host "Refactoring $service..."
    $serviceSnake = $service.Replace('-', '_')
    $basePackage = "com.ecommerce.$serviceSnake"
    $srcMain = "C:\spring-workspace\ecommerce-microservices-platform\$service\src\main\java"
    $baseDir = "$srcMain\com\ecommerce\$serviceSnake"
    
    if (-not (Test-Path $srcMain)) { continue }

    $allFiles = Get-ChildItem -Path $srcMain -Recurse -File -Filter *.java
    
    $wildcards = @"
import ${basePackage}.*;
import ${basePackage}.config.*;
import ${basePackage}.controller.*;
import ${basePackage}.dto.*;
import ${basePackage}.dto.request.*;
import ${basePackage}.dto.response.*;
import ${basePackage}.entity.*;
import ${basePackage}.repository.*;
import ${basePackage}.service.*;
import ${basePackage}.service.impl.*;
import ${basePackage}.exception.*;
import ${basePackage}.mapper.*;
import ${basePackage}.security.*;
import ${basePackage}.kafka.producer.*;
import ${basePackage}.kafka.consumer.*;
import ${basePackage}.kafka.event.*;
import ${basePackage}.util.*;
import ${basePackage}.validation.*;
"@

    foreach ($file in $allFiles) {
        $content = Get-Content $file.FullName -Raw
        $name = $file.Name
        
        $suffix = ""
        
        if ($name -match "Application\.java") { $suffix = "" }
        elseif ($content -match "@Entity" -or $content -match "@Table") { $suffix = "entity" }
        elseif ($content -match "extends JpaRepository" -or $name -match "Repository\.java") { $suffix = "repository" }
        elseif ($content -match "@RestController" -or $name -match "Controller\.java") { $suffix = "controller" }
        elseif ($content -match "@Service" -or $name -match "ServiceImpl\.java") { $suffix = "service.impl" }
        elseif ($content -match "public interface.*Service" -or $name -match "Service\.java") { $suffix = "service" }
        elseif ($name -match "Request\.java") { $suffix = "dto.request" }
        elseif ($name -match "Response\.java") { $suffix = "dto.response" }
        elseif ($name -match "Event\.java") { $suffix = "kafka.event" }
        elseif ($name -match "Dto\.java") { $suffix = "dto" }
        elseif ($content -match "KafkaTemplate" -or $name -match "Publisher") { $suffix = "kafka.producer" }
        elseif ($content -match "@KafkaListener" -or $name -match "Consumer") { $suffix = "kafka.consumer" }
        elseif ($name -match "Jwt" -or $content -match "@EnableWebSecurity" -or $name -match "SecurityConfig") { $suffix = "security" }
        elseif ($name -match "Exception" -or $content -match "@ControllerAdvice") { $suffix = "exception" }
        elseif ($content -match "@Configuration" -or $name -match "Config\.java") { $suffix = "config" }
        elseif ($name -match "Mapper\.java") { $suffix = "mapper" }
        elseif ($name -match "Validator\.java") { $suffix = "validation" }
        else {
            # Fallbacks based on old directory
            if ($file.DirectoryName -match "dto") { $suffix = "dto" }
            elseif ($file.DirectoryName -match "service") { $suffix = "service" }
            elseif ($file.DirectoryName -match "entity|model") { $suffix = "entity" }
            elseif ($file.DirectoryName -match "controller") { $suffix = "controller" }
            elseif ($file.DirectoryName -match "repository|persistence") { $suffix = "repository" }
            elseif ($file.DirectoryName -match "exception") { $suffix = "exception" }
            elseif ($file.DirectoryName -match "config") { $suffix = "config" }
            elseif ($file.DirectoryName -match "security") { $suffix = "security" }
            elseif ($file.DirectoryName -match "messaging") { $suffix = "kafka" }
            else { $suffix = "util" }
        }

        $newPackage = if ($suffix) { "$basePackage.$suffix" } else { $basePackage }
        $newDir = if ($suffix) { "$baseDir\" + $suffix.Replace('.', '\') } else { $baseDir }
        
        if (-not (Test-Path $newDir)) {
            New-Item -ItemType Directory -Force -Path $newDir | Out-Null
        }
        
        $newPath = Join-Path $newDir $name

        # Rewrite content
        # 1. Replace package
        $content = $content -replace "(?m)^package\s+com\.ecommerce\.[^;]+;", "package $newPackage;"
        
        # 2. Remove all internal imports
        $content = $content -replace "(?m)^import\s+com\.ecommerce\.[^;]+;\r?\n?", ""
        
        # 3. Inject wildcards after package
        $content = $content -replace "(?m)^(package\s+${newPackage};)", "`$1`n`n$wildcards"

        # Save to new location
        if ($file.FullName -ne $newPath) {
            $content | Out-File -FilePath $newPath -Encoding utf8
            Remove-Item $file.FullName
        } else {
            $content | Out-File -FilePath $file.FullName -Encoding utf8
        }
    }
}
Write-Host "Refactoring files complete."
