
# Report

## Code Gen And Ocl

| Test | Status | Reason |
| --- | --- | --- |
| Pass Instance1 | ❌ Failed | ⚠️ FileNotFoundException |
| Fail Instance2Exists | ❌ Failed | Instance that fails validation must be in file model/c2/fail.xmi |
| Pass Instance2Exists | ❌ Failed | Instance that passes validation must be in file model/c2/pass.xmi |
| Pass Instance2 | ❌ Failed | ⚠️ FileNotFoundException |
| Code Generated | ❌ Failed | Directory of generated files does not exist. |
| Pass Instance1Exists | ❌ Failed | Instance that passes validation must be in file model/c1/pass.xmi |
| Fail Instance1Exists | ❌ Failed | Instance that fails validation must be in file model/c1/fail.xmi |
| Fail Instance1 | ❌ Failed | ⚠️ FileNotFoundException |
| Fail Instance2 | ❌ Failed | ⚠️ FileNotFoundException |
## EMFModel

| Test | Status | Reason |
| --- | --- | --- |
| Num OCL | ❌ Failed | Make sure to define at least 2 OCL constraints. |
| Five Or More Classes | ❌ Failed | Make sure to define at least 5 classes. |
| Model Exists | ✅ Passed | - |
| Num Assocs | ❌ Failed | Make sure to define at least 4 associations. |
| Num Attributes | ❌ Failed | Make sure to define at least 8 attributes. |
| Model Validates | ✅ Passed | - |
## Ecore Ocl

| Test | Status | Reason |
| --- | --- | --- |
