$services = @("api-gateway", "user-service", "product-service", "cart-service", "order-service", "payment-service", "notification-service")
$results = @{}

foreach ($service in $services) {
    $srcMain = "C:\spring-workspace\ecommerce-microservices-platform\$service\src\main\java"
    $srcTest = "C:\spring-workspace\ecommerce-microservices-platform\$service\src\test\java"
    
    $stats = @{
        Entities = 0
        Repositories = 0
        Controllers = 0
        ServiceInterfaces = 0
        ServiceImplementations = 0
        DTOs = 0
        KafkaProducers = 0
        KafkaConsumers = 0
        SecurityClasses = 0
        TestClasses = 0
        EmptyPackages = @()
        DuplicatePackages = @()
        DuplicateClasses = @()
        MissingFeatures = @()
        PackageInconsistencies = @()
    }

    if (Test-Path $srcMain) {
        $allFiles = Get-ChildItem -Path $srcMain -Recurse -File -Filter *.java
        $allDirs = Get-ChildItem -Path $srcMain -Recurse -Directory
        
        $classNames = @{}
        
        if ($allFiles) {
            foreach ($file in $allFiles) {
                $content = Get-Content $file.FullName -Raw
                $name = $file.Name
                
                # Check for duplicates
                if ($classNames.ContainsKey($name)) {
                    $stats.DuplicateClasses += $name
                } else {
                    $classNames[$name] = 1
                }

                # Entities
                if ($content -match "@Entity" -or $content -match "@Table" -or $file.DirectoryName -match "entity|entities|model") {
                    if ($content -notmatch "public interface" -and $content -match "class ") {
                        $stats.Entities++
                    }
                }
                
                # Repositories
                if ($content -match "extends JpaRepository" -or $name -match "Repository\.java" -or $file.DirectoryName -match "persistence|repository") {
                    $stats.Repositories++
                }
                
                # Controllers
                if ($content -match "@RestController" -or $file.DirectoryName -match "controller") {
                    $stats.Controllers++
                }
                
                # Service Interfaces and Impls
                if ($file.DirectoryName -match "service" -and $name -notmatch "Application\.java") {
                    if ($content -match "public interface") {
                        $stats.ServiceInterfaces++
                    } elseif ($content -match "@Service" -or $content -match "public class.*implements") {
                        $stats.ServiceImplementations++
                    } elseif ($content -match "public class") {
                        # Might just be a service class without interface
                        $stats.ServiceImplementations++
                    }
                }
                
                # DTOs
                if ($file.DirectoryName -match "dto" -or $name -match "Dto\.java|Request\.java|Response\.java|Event\.java") {
                    $stats.DTOs++
                }
                
                # Kafka Producers
                if ($content -match "KafkaTemplate" -or $content -match "publish") {
                    if ($name -notmatch "Application\.java") {
                        $stats.KafkaProducers++
                    }
                }
                
                # Kafka Consumers
                if ($content -match "@KafkaListener") {
                    $stats.KafkaConsumers++
                }
                
                # Security Classes
                if ($file.DirectoryName -match "security" -or $content -match "@EnableWebSecurity" -or $name -match "Jwt") {
                    $stats.SecurityClasses++
                }
            }
        }
        
        # Package analysis
        if ($allDirs) {
            foreach ($dir in $allDirs) {
                $filesInDir = Get-ChildItem -Path $dir.FullName -File
                $dirsInDir = Get-ChildItem -Path $dir.FullName -Directory
                if ($filesInDir.Count -eq 0 -and $dirsInDir.Count -eq 0) {
                    $stats.EmptyPackages += $dir.FullName.Replace($srcMain, "")
                }
                
                # Check package consistency
                $relPath = $dir.FullName.Replace($srcMain, "").Replace("\", ".")
                $serviceName1 = $service.Replace('-', '_')
                $serviceName2 = $service.Replace('-', '')
                if ($relPath -match "com\.ecommerce" -and $relPath -notmatch "com\.ecommerce\.$serviceName1" -and $relPath -notmatch "com\.ecommerce\.$serviceName2") {
                    # Ignore base com.ecommerce
                    if ($relPath -ne ".com.ecommerce" -and $relPath -ne ".com") {
                       $stats.PackageInconsistencies += $relPath
                    }
                }
                
                # Check duplicate packages (e.g. user_service vs userservice)
                if ($relPath -match "com\.ecommerce\.$serviceName2") {
                    $stats.DuplicatePackages += $relPath
                }
            }
        }
        
        # Missing features
        if ($stats.Entities -eq 0 -and $service -ne "api-gateway") { $stats.MissingFeatures += "Entities" }
        if ($stats.Repositories -eq 0 -and $service -ne "api-gateway") { $stats.MissingFeatures += "Repositories" }
        if ($stats.Controllers -eq 0 -and $service -ne "notification-service" -and $service -ne "api-gateway") { $stats.MissingFeatures += "Controllers" }
        if ($stats.ServiceImplementations -eq 0 -and $service -ne "api-gateway") { $stats.MissingFeatures += "ServiceImplementations" }
    }
    
    if (Test-Path $srcTest) {
        $testFiles = Get-ChildItem -Path $srcTest -Recurse -File -Filter *.java
        if ($testFiles) {
            $stats.TestClasses = @($testFiles).Count
        }
    }
    
    $results[$service] = $stats
}

$results | ConvertTo-Json -Depth 5 | Out-File -FilePath C:\spring-workspace\ecommerce-microservices-platform\audit_results.json -Encoding utf8
Write-Host "Audit script completed."
