/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("file")
public class FileResource {

    @Context
    private UriInfo context;

    public FileResource() {
    }

//    @POST
//    @Path("uploadImage")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_XML)
//    public void uploadImage(
//            @FormDataParam("fileReference") String fileReference,
//            @FormDataParam("caption") String caption,
//            @FormDataParam("file") InputStream uploadedFileInputStream,
//            @FormDataParam("file") FormDataContentDisposition uploadedFileDetails) {
//        try {
//            if (fileReference != null && fileReference.trim().length()) {
//                Long fileReferenceLong = Long.parseLong(fileReference);
//
//                if (uploadedFileDetails.getSize() <= 2097152) {
//                    String[] fileNameParts = uploadedFileDetails.getFileName().split("\\.");
//
//                    if ((fileNameParts[1].equals("jpg"))
//                            || (fileNameParts[1].equals("jpeg")) || (fileNameParts[1].equals("gif"))
//                            || (fileNameParts[1].equals("png"))) {
//                        String newFilename = UUID.randomUUID().toString();
//
//                        File outputFile = new File("C:/" + newFilename + "." + fileNameParts[1]);
//                        FileOutputStream out = new FileOutputStream(outputFile);
//
//                        int a;
//                        int BUFFER_SIZE = 8192;
//                        byte[] buffer = new byte[BUFFER_SIZE];
//
//                        while (true) {
//                            a = uploadedFileInputStream.read(buffer);
//                            if (a < 0) {
//                                break;
//                            }
//                            out.write(buffer, 0, a);
//                            out.flush();
//                        }
//
//                        out.close();
//                        uploadedFileInputStream.close();
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

}
