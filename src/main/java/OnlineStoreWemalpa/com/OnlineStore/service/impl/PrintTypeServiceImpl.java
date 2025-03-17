package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.PrintType;
import OnlineStoreWemalpa.com.OnlineStore.repository.PrintTypeRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.PrintTypeService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class PrintTypeServiceImpl implements PrintTypeService {
    private final PrintTypeRepository repository;
    @Override
    public List<PrintType> findAllPrintType() {
        return repository.findAll();
    }

    @Override
    public PrintType savePrintType(PrintType printType) {
        return repository.save(printType);
    }

    @Override
    public PrintType updatePrintType(PrintType printType) {
        return repository.save(printType);
    }

    @Override
    public void deletePrintType(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PrintType findById(Long id) { return repository.findById(id).orElse(null); }

    @Override
    public boolean existsByName(String name) { return repository.existsByName(name); }
}
