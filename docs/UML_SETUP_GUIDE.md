# UML Diagrams Setup Guide

## 🔧 Fixing Graphviz Issue in IntelliJ IDEA

### Problem
The PlantUML plugin shows "Dot executable does not exist. Cannot find Graphviz" error when trying to render diagrams.

### Solution for macOS

#### 1. Install Graphviz
Choose one of these methods:

**Using Homebrew (Recommended):**
```bash
brew install graphviz
```

**Using MacPorts:**
```bash
sudo port install graphviz
```

**Manual Installation:**
- Download from: https://graphviz.org/download/
- Install the macOS package

#### 2. Verify Installation
```bash
# Check if dot command is available
which dot
dot -V

# Expected output paths:
# Apple Silicon Mac: /opt/homebrew/bin/dot
# Intel Mac: /usr/local/bin/dot
```

#### 3. Configure IntelliJ IDEA

**Method 1: Automatic Detection (Usually works after restart)**
1. Restart IntelliJ IDEA after installing Graphviz
2. Open any `.puml` file
3. The plugin should auto-detect Graphviz

**Method 2: Manual Configuration**
1. Go to **IntelliJ IDEA → Preferences** (or **Settings** on Windows/Linux)
2. Navigate to **Tools → PlantUML**
3. Set **Graphviz dot executable** to:
   - Apple Silicon: `/opt/homebrew/bin/dot`
   - Intel Mac: `/usr/local/bin/dot`
   - Or use: `which dot` command output
4. Click **Test** to verify
5. Click **OK** to save

#### 4. Alternative: Environment Variable
Add to your shell profile (`~/.zshrc` or `~/.bash_profile`):
```bash
export PATH="/opt/homebrew/bin:$PATH"  # Apple Silicon
# or
export PATH="/usr/local/bin:$PATH"     # Intel Mac
```

## 📊 Enhanced UML Diagrams Features

### Styling Improvements
- **Modern Theme**: Applied "toy" theme for better readability
- **Color Coding**: Each pattern has distinct colors for visual organization
- **Orthogonal Lines**: Cleaner connection lines between classes
- **Package Organization**: Logical grouping by pattern category

### Content Enhancements
- **Bilingual Titles**: English and Chinese pattern names
- **Detailed Annotations**: Notes explaining key concepts
- **Proper UML Notation**: Correct use of relationships (inheritance, composition, aggregation)
- **Method Visibility**: Public (+), Protected (#), Private (-), Static indicators
- **Layout Optimization**: Hidden connections for better diagram organization

### Diagram Contents

#### 1. Creational Patterns (`creational-patterns.puml`)
- **5 Patterns**: Singleton, Factory Method, Abstract Factory, Builder, Prototype
- **Real Examples**: Database connections, UI factories, computer builder
- **Modern Java**: Enum singleton, fluent interfaces, generic types

#### 2. Structural Patterns (`structural-patterns.puml`)
- **7 Patterns**: Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy
- **Practical Use Cases**: Media adaptation, file systems, coffee customization
- **Multiple Proxy Types**: Virtual, caching, protection proxies illustrated

#### 3. Behavioral Patterns (`behavioral-patterns.puml`)
- **11 Patterns**: Observer, Strategy, Command, State, Template Method, Chain of Responsibility, Iterator, Mediator, Memento, Visitor, Interpreter
- **Complex Interactions**: State machines, command queues, expression trees
- **Real-World Scenarios**: Weather stations, payment systems, support tickets

## 🚀 Usage Instructions

### In IntelliJ IDEA
1. **Install PlantUML Plugin**: Go to Plugins → Install "PlantUML Integration"
2. **Open UML Files**: Navigate to `src/main/resources/uml/`
3. **Live Preview**: Right-click on `.puml` file → "Show PlantUML Diagram"
4. **Split View**: Enable split editor to see code and diagram simultaneously

### Generate Images
```bash
# From project root directory
cd src/main/resources/uml

# Generate PNG images
plantuml *.puml

# Generate SVG for web
plantuml -tsvg *.puml

# Generate PDF for documentation
plantuml -tpdf *.puml

# Generate all formats
plantuml -tpng -tsvg -tpdf *.puml
```

### Online Alternative
If local setup fails, use PlantUML online:
1. Copy diagram code
2. Visit: http://www.plantuml.com/plantuml/uml/
3. Paste code and generate diagram

## 🎯 Benefits of Visual Documentation

### For Learning
- **Pattern Recognition**: Quickly identify pattern structures
- **Relationship Understanding**: See how classes interact
- **Memory Aid**: Visual learning complements code examples

### For Development
- **Architecture Overview**: High-level system design view
- **Code Reviews**: Visual discussion of design decisions
- **Documentation**: Professional diagrams for technical docs

### For Teams
- **Common Language**: UML provides standardized notation
- **Design Communication**: Share architectural concepts visually
- **Training Material**: Onboard new team members effectively

## 🔍 Troubleshooting

### Common Issues

**1. "Permission denied" error:**
```bash
sudo chmod +x /opt/homebrew/bin/dot
```

**2. Plugin not responding:**
- Restart IntelliJ IDEA
- Invalidate caches: File → Invalidate Caches and Restart

**3. Diagram not updating:**
- Force refresh: Right-click → "Reload from disk"
- Check file syntax for PlantUML errors

**4. Large diagrams render slowly:**
- Consider splitting into smaller diagrams
- Use `skinparam dpi 150` for higher quality
- Enable `skinparam linetype ortho` for cleaner lines

### Verification Steps
1. ✅ Graphviz installed and in PATH
2. ✅ IntelliJ PlantUML plugin installed
3. ✅ Plugin configured with correct dot path
4. ✅ Test with simple diagram first
5. ✅ Check console for error messages

## 📝 Next Steps

1. **Install Graphviz** using the instructions above
2. **Configure IntelliJ IDEA** with the correct dot executable path
3. **Open UML files** in `src/main/resources/uml/`
4. **Explore patterns visually** alongside the Java implementations
5. **Generate documentation** by exporting diagrams to images

The enhanced UML diagrams now provide a comprehensive visual companion to your Java code examples, making it easier to understand and teach the GoF design patterns!
