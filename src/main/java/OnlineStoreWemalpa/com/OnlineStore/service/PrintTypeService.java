package OnlineStoreWemalpa.com.OnlineStore.service;
import OnlineStoreWemalpa.com.OnlineStore.model.PrintType;

import java.util.List;

public interface PrintTypeService {
    List<PrintType> findAllPrintType();
    PrintType savePrintType(PrintType printType);
    PrintType updatePrintType(PrintType printType);
    void deletePrintType(Long id);
    PrintType findById(Long id);
    boolean existsByName(String name);
}
