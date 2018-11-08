package br.edu.ulbra.election.candidate.service;

import br.edu.ulbra.election.candidate.exception.GenericOutputException;
import br.edu.ulbra.election.candidate.input.v1.CandidateInput;
import br.edu.ulbra.election.candidate.model.Candidate;
import br.edu.ulbra.election.candidate.output.v1.GenericOutput;
import br.edu.ulbra.election.candidate.output.v1.CandidateOutput;
import br.edu.ulbra.election.candidate.repository.CandidateRepository;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CandidateRepository CandidateRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private static final String MESSAGE_INVALID_ID = "Invalid id";
    private static final String MESSAGE_Candidate_NOT_FOUND = "Candidate not found";

    @Autowired
    public CandidateService(CandidateRepository CandidateRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.CandidateRepository = CandidateRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CandidateOutput> getAll(){
        Type CandidateOutputListType = new TypeToken<List<CandidateOutput>>(){}.getType();
        return modelMapper.map(CandidateRepository.findAll(), CandidateOutputListType);
    }

    public CandidateOutput create(CandidateInput CandidateInput) {
        validateInput(CandidateInput, false);
        Candidate Candidate = modelMapper.map(CandidateInput, Candidate.class);
        Candidate.setPassword(passwordEncoder.encode(Candidate.getPassword()));
        Candidate = CandidateRepository.save(Candidate);
        return modelMapper.map(Candidate, CandidateOutput.class);
    }

    public CandidateOutput getById(Long CandidateId){
        if (CandidateId == null){
            throw new GenericOutputException(MESSAGE_INVALID_ID);
        }

        Candidate Candidate = CandidateRepository.findById(CandidateId).orElse(null);
        if (Candidate == null){
            throw new GenericOutputException(MESSAGE_Candidate_NOT_FOUND);
        }

        return modelMapper.map(Candidate, CandidateOutput.class);
    }

    public CandidateOutput update(Long CandidateId, CandidateInput CandidateInput) {
        if (CandidateId == null){
            throw new GenericOutputException(MESSAGE_INVALID_ID);
        }
        validateInput(CandidateInput, true);

        Candidate Candidate = CandidateRepository.findById(CandidateId).orElse(null);
        if (Candidate == null){
            throw new GenericOutputException(MESSAGE_Candidate_NOT_FOUND);
        }

        Candidate.setEmail(CandidateInput.getEmail());
        Candidate.setName(CandidateInput.getName());
        if (!StringUtils.isBlank(CandidateInput.getPassword())) {
            Candidate.setPassword(passwordEncoder.encode(CandidateInput.getPassword()));
        }
        Candidate = CandidateRepository.save(Candidate);
        return modelMapper.map(Candidate, CandidateOutput.class);
    }

    public GenericOutput delete(Long CandidateId) {
        if (CandidateId == null){
            throw new GenericOutputException(MESSAGE_INVALID_ID);
        }

        Candidate Candidate = CandidateRepository.findById(CandidateId).orElse(null);
        if (Candidate == null){
            throw new GenericOutputException(MESSAGE_Candidate_NOT_FOUND);
        }

        CandidateRepository.delete(Candidate);

        return new GenericOutput("Candidate deleted");
    }

    private void validateInput(CandidateInput CandidateInput, boolean isUpdate){
        if (StringUtils.isBlank(CandidateInput.getEmail())){
            throw new GenericOutputException("Invalid email");
        }
        if (StringUtils.isBlank(CandidateInput.getName())){
            throw new GenericOutputException("Invalid name");
        }
        if (!StringUtils.isBlank(CandidateInput.getPassword())){
            if (!CandidateInput.getPassword().equals(CandidateInput.getPasswordConfirm())){
                throw new GenericOutputException("Passwords doesn't match");
            }
        } else {
            if (!isUpdate) {
                throw new GenericOutputException("Password doesn't match");
            }
        }
    }

}
