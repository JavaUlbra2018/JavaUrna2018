package br.edu.ulbra.election.party.services;

import br.edu.ulbra.election.party.input.v1.PartyInput;
import br.edu.ulbra.election.party.model.Party;
import br.edu.ulbra.election.party.output.v1.GenericOutput;
import br.edu.ulbra.election.party.output.v1.PartyOutput;
import br.edu.ulbra.election.party.repository.PartyRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class PartyService {

    private final PartyRepository partyRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PartyService(PartyRepository partyRepository, ModelMapper modelMapper){
        this.partyRepository = partyRepository;
        this.modelMapper = modelMapper;
    }

    public List<PartyOutput> getAll(){
        Type partyOutputListType = new TypeToken<List<PartyOutput>>(){}.getType();
        return modelMapper.map(partyRepository.findAll(), partyOutputListType);
    }

    public PartyOutput create(PartyInput partyInput) {
        Party party = modelMapper.map(partyInput, Party.class);
        party = partyRepository.save(party);
        return modelMapper.map(party, PartyOutput.class);
    }

    public PartyOutput getById(Long partyId){
        Party party = partyRepository.findById(partyId).orElse(null);
        return modelMapper.map(party, PartyOutput.class);
    }

    public PartyOutput update(Long partyId, PartyInput partyInput) {
    	Party party = partyRepository.findById(partyId).orElse(null);
    	party.setNumber(partyInput.getNumber());
    	party.setName(partyInput.getName());
    	party.setCode(partyInput.getCode());
    	party = partyRepository.save(party);
    	return modelMapper.map(party, PartyOutput.class);
    }

    public GenericOutput delete(Long partyId) {
    	Party party = partyRepository.findById(partyId).orElse(null);
        partyRepository.delete(party);
        return new GenericOutput("Party deleted");
    }

}
