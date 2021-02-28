package br.ce.wcaquino.repositorio;

import br.ce.wcaquino.entidades.Invoice;

import java.util.List;

public interface InvoiceRepository {
    List<Invoice> all();
}
